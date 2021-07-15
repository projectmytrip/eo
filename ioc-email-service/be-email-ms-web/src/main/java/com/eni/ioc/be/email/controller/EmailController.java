package com.eni.ioc.be.email.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.be.email.common.Response;
import com.eni.ioc.be.email.common.ResultResponse;
import com.eni.ioc.be.email.config.EmailSenderProperties;
import com.eni.ioc.be.email.dto.NotificationBean;
import com.eni.ioc.be.email.dto.input.Notification;
import com.eni.ioc.be.email.service.NotificationService;
import com.eni.ioc.be.email.util.RunnableUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;

@RestController
// @ComponentScan
@RequestMapping("/email")
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private NotificationService service;

	@Autowired
	private ObjectMapper objectMapper;

	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody Response<?> ping(HttpServletRequest request) {
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, OK);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody Response<?> sendEmail(@RequestBody Notification notification, HttpServletRequest request)
			throws Exception {

		NotificationBean nb = new NotificationBean();
		ResultResponse resultResponse = null;

		notification.toBean(nb);

		if (nb.getRecipients() == null || nb.getRecipients().isEmpty()) {
			logger.error("-- NO RECIPIENTS ERROR, notification by email NOT sent --");
			resultResponse = new ResultResponse(codeKO, KO);
			return buildResponse(resultResponse, "A Recipients can't be empty");
		}

		Runnable runnable = new RunnableUtil(service, nb, null);
		new Thread(runnable).start();

		logger.info("-- Notification sent by email --");
		resultResponse = new ResultResponse(codeOK, OK);
		return buildResponse(resultResponse, "Notification sent by email");
	}

	public void sendEmail(Notification notification) throws Exception {

		NotificationBean nb = new NotificationBean();

		notification.toBean(nb);

		if (nb.getRecipients() == null || nb.getRecipients().isEmpty()) {
			logger.error("-- NO RECIPIENTS ERROR, notification by email NOT sent --");
			throw new Exception("There are no recipients");
		}

		//Runnable runnable = new RunnableUtil(service, nb, null);
		Runnable runnable = null;
		if(notification.getAttachment() != null){
			logger.info("Sono presenti allegati");
			File[] files = new File[1];
			files[0] = toFile(notification.getAttachment(), notification.getAttachmentName());
			runnable = new RunnableUtil(service, nb, files);
		}else{
			logger.info("Non sono presenti allegati");
			runnable = new RunnableUtil(service, nb, null);
		}
		
		new Thread(runnable).start();

		logger.info("-- Notification sent by email --");

	}

	@PostMapping(value = "/emailWithAttach", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response<?> emailWithAttach(//
			HttpServletRequest request, //
			@RequestParam("uploadfile") final MultipartFile multipartFile, //
			@RequestParam("notification") String stringNotification) throws Exception {

		if (multipartFile == null) {
			throw new Exception("MultipartFile can't be null");
		}

		try {
			Notification notification = deserializeIssue(stringNotification);
			NotificationBean nb = new NotificationBean();
			notification.toBean(nb);

			if (nb.getRecipients() == null || nb.getRecipients().isEmpty()) {
				throw new Exception("A Recipients can't be empty");
			}

			File fileToUpload = toFile(multipartFile);

			File[] files = new File[1];
			files[0] = fileToUpload;

			Runnable runnable = new RunnableUtil(service, nb, files);
			new Thread(runnable).start();

			return buildResponse(null, request);
		} catch (IllegalArgumentException e) {
			throw new Exception(e.getMessage());
		}
	}

	@PostMapping(value = "/emailPodStatus")
	public @ResponseBody Response<?> emailPodStatus(//
			HttpServletRequest request, //
			@RequestParam("uploadfile") final MultipartFile multipartFile) throws Exception {

		if (multipartFile == null) {
			throw new Exception("MultipartFile can't be null");
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
			service.sendMorningCheck(reader);
		}
		return buildResponse(null, "Morning check sent by email");
	}

	private static File toFile(MultipartFile multipartFile) {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = File.createTempFile("_ms",
					String.format("%s%s", EmailSenderProperties.FILE_PREFIX, multipartFile.getOriginalFilename()));
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
		} catch (IOException e) {
			logger.warn(String.format("Error furing convert a multipart file to file: '%s'", multipartFile.getName()));
		} finally {
			closeFos(fos);
		}
		return file;
	}

	private static File toFile(byte[] fileBytes, String fileName) {
		File file = null;
		FileOutputStream fos = null;
		try {
			file = File.createTempFile("_ms",
					String.format("%s%s", EmailSenderProperties.FILE_PREFIX, fileName));
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(fileBytes);
		} catch (IOException e) {
			logger.warn(String.format("Error furing convert a multipart file to file: '%s'", fileName));
		} finally {
			closeFos(fos);
		}
		return file;
	}
	
	private static void closeFos(FileOutputStream fos){
		if(fos != null){	
			try {
				fos.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private Notification deserializeIssue(String content) throws IllegalArgumentException, ParseException {
		Notification output;
		String erroMsg = "Error during deserialize the issue content";
		try {
			output = objectMapper.readValue(StringEscapeUtils.unescapeJson(content), Notification.class);
			logger.debug(String.format("Deserialized Issue: '%s'", content));
		} catch (IOException | JsonSyntaxException e) {
			output = null;
			logger.warn(String.format("%s: '%s'", erroMsg, content), e);
		}

		if (output == null)
			throw new IllegalArgumentException("Output is null after deserialization");

		return output;
	}

	private <T> Response<T> buildResponse(ResultResponse result, T data) {
		Response<T> response = new Response<T>();
		if (result == null) {
			ResultResponse myResult = new ResultResponse();
			myResult.setCode(HttpStatus.OK.value());
			myResult.setMessage(HttpStatus.OK.getReasonPhrase());
			response.setResult(myResult);
		} else {
			response.setResult(result);
		}
		response.setData(data);
		return response;
	}

}
