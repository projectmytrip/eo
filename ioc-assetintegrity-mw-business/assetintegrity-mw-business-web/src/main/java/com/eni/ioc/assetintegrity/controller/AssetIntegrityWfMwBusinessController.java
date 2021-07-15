package com.eni.ioc.assetintegrity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.assetintegrity.common.Response;
import com.eni.ioc.assetintegrity.common.ResultResponse;
import com.eni.ioc.assetintegrity.dto.CheckSignalsWF;
import com.eni.ioc.assetintegrity.service.AssetIntegrityServiceWf;

@RestController
@RequestMapping("/assetintegrity")
public class AssetIntegrityWfMwBusinessController {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityWfMwBusinessController.class);
	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";

	@Autowired
	AssetIntegrityServiceWf assetIntegrityService;

	// JUST INTERNAL
	@GetMapping(value = { "/openCriticalSignalsWaringWF/{asset}" })
	public @ResponseBody Response<?> openCriticalSignalsWaringWF(HttpServletRequest request,
			@PathVariable("asset") String asset) {

		logger.debug("openCriticalSignalsBySeverityWF/{}", asset);

		try {
			assetIntegrityService.openCriticalSignalsWaringWF(asset);

			ResultResponse resultResponse = new ResultResponse(codeOK, OK);
			return buildResponse(resultResponse, null);
		} catch (Exception e) {
			logger.error("Error during call openCriticalSignalsBySeverityWF ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}
	
	// JUST INTERNAL
	@PostMapping(value = { "/pollingMOC/{asset}" })
	public @ResponseBody Response<?> pollingMOC(HttpServletRequest request, @RequestBody List<String> input,
			@PathVariable("asset") String asset) {

		logger.debug("pollingMOC/{}", asset);

		try {
			ResultResponse resultResponse = new ResultResponse(codeOK, OK);
			return buildResponse(resultResponse, assetIntegrityService.pollingMOC(input, asset));
		} catch (Exception e) {
			logger.error("Error during call pollingMOC ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	
	@GetMapping(value = { "/checkSignalsStatus/{asset}/{wfId}", "/public/checkSignalsStatus/{asset}/{wfId}" })
	public @ResponseBody Response<?> checkSignalsStatus(HttpServletRequest request, @PathVariable("asset") String asset,
			@PathVariable("wfId") String wfId) {

		logger.debug("checkSignalsStatus/{}", wfId);

		try {
			CheckSignalsWF check = assetIntegrityService.checkSignalsStatus(asset, wfId);
			ResultResponse resultResponse = new ResultResponse(codeOK, OK);
			return buildResponse(resultResponse, check);
		} catch (Exception e) {
			logger.error("Error during call checkSignalsStatus ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
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
