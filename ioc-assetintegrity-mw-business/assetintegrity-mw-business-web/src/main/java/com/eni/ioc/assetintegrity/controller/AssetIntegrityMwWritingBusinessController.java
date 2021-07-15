package com.eni.ioc.assetintegrity.controller;


import java.util.List;

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

import com.eni.ioc.assetintegrity.common.Response;
import com.eni.ioc.assetintegrity.common.ResultResponse;
import com.eni.ioc.assetintegrity.dto.KpiDataDto;
import com.eni.ioc.assetintegrity.service.AssetIntegrityService;


@RestController
@RequestMapping("/assetintegrity/w")
public class AssetIntegrityMwWritingBusinessController{
	
	private static final Logger	logger	= LoggerFactory.getLogger(AssetIntegrityMwWritingBusinessController.class);
	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";
	public static final String FORBIDDEN = "FORBIDDEN";
	@Autowired
	AssetIntegrityService assetIntegrityService;
	

    @PostMapping(value = {"/updateDetails/{asset}/{table}", "/public/updateDetails/{asset}/{table}"})
    public @ResponseBody
    Response<?> updateKpiData(@RequestBody List<KpiDataDto> data, 
            @PathVariable("table") String table,
            HttpServletRequest request) {
        ResultResponse resultResponse = null;
        try {
                assetIntegrityService.updateKpiData(data, table);
                resultResponse = new ResultResponse(codeOK, OK);
                logger.debug("User authorized update kpi data");

            return buildResponse(resultResponse, null);
        } catch (Exception e) {
            logger.error("Error during call updateDetails ", e);
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