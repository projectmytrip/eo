package com.eni.ioc.assetintegrity.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.assetintegrity.api.OpenWorkflowCriticalSignalsInput;
import com.eni.ioc.assetintegrity.api.RequestModificationInput;
import com.eni.ioc.assetintegrity.common.Response;
import com.eni.ioc.assetintegrity.common.ResultResponse;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.service.AssetIntegrityServiceWf;
import com.eni.ioc.assetintegrity.utils.JWTUtils;

@RestController
@RequestMapping("/assetintegrity/w")
public class AssetIntegrityWfMwWritingBusinessController {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityWfMwWritingBusinessController.class);
	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";

	@Autowired
	AssetIntegrityServiceWf assetIntegrityService;

	@PostMapping(value = { "/openWorkflowCriticalSignals/{Asset}", "/public/openWorkflowCriticalSignals/{Asset}" })
	public @ResponseBody Response<?> criticalSignal(HttpServletRequest request,
			@RequestBody OpenWorkflowCriticalSignalsInput input, @PathVariable("Asset") String asset) {
		logger.debug("openWorkflowCriticalSignals");
		ResultResponse resultResponse = null;

		try {
			String jwt = JWTUtils.getJWTfromHeader(request);

			String user = JWTUtils.getUser(jwt);
			String uid = JWTUtils.getUid(jwt);
			assetIntegrityService.openWorkflowCriticalSignals(input, asset, user, uid);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get backlogList");

			logger.debug("end");
			return buildResponse(resultResponse, "OK");
		} catch (Exception e) {
			logger.error("Error during call backlogList ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}
	
	@PostMapping(value = { "/requestModification/{Asset}", "/public/requestModification/{Asset}" })
	public @ResponseBody Response<?> requestModification(HttpServletRequest request,
			@RequestBody RequestModificationInput input, @PathVariable("Asset") String asset) {
		logger.debug("openWorkflowCriticalSignals");
		ResultResponse resultResponse = null;

		try {
			String jwt = JWTUtils.getJWTfromHeader(request);

			String user = JWTUtils.getUser(jwt);
			String uid = JWTUtils.getUid(jwt);
			assetIntegrityService.requestModification(input, asset, user, uid);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get backlogList");

			logger.debug("end");
			return buildResponse(resultResponse, "OK");
		} catch (Exception e) {
			logger.error("Error during call backlogList ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@PostMapping(value = { "/updateSignalMoc/{Asset}/", "/public/updateSignalMoc/{Asset}/" })
	public @ResponseBody Response<?> updateSignalMoc(HttpServletRequest request, @RequestBody CriticalSignalDto input,
			@PathVariable("Asset") String asset) {
		logger.debug("updateSignalMoc");
		ResultResponse resultResponse = null;

		try {
			String jwt = JWTUtils.getJWTfromHeader(request);

			String uid = JWTUtils.getUid(jwt);
			assetIntegrityService.updateSignalMoc(input, asset, uid);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get updateSignalMoc");

			logger.debug("end");
			return buildResponse(resultResponse, "OK");
		} catch (Exception e) {
			logger.error("Error during call backlogList ", e);
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
