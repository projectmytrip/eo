package com.eni.ioc.dailyworkplan.controller;

import java.io.ByteArrayOutputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.eni.ioc.dailyworkplan.api.PlanningCopy;
import com.eni.ioc.dailyworkplan.api.PlanningFilter;
import com.eni.ioc.dailyworkplan.api.ReportFilter;
import com.eni.ioc.dailyworkplan.api.UserFilter;
import com.eni.ioc.dailyworkplan.common.Response;
import com.eni.ioc.dailyworkplan.common.ResultResponse;
import com.eni.ioc.dailyworkplan.dto.PlanningSaveRequest;
import com.eni.ioc.dailyworkplan.dto.ReportDto;
import com.eni.ioc.dailyworkplan.service.DailyWorkPlanService;
import com.eni.ioc.dailyworkplan.service.utils.ProfileUtils;

@RestController
@RequestMapping("/dailyworkplan")
public class DailyWorkPlanController {

	private static final Logger logger = LoggerFactory.getLogger(DailyWorkPlanController.class);
	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";

	@Autowired
	DailyWorkPlanService dailyWorkPlanService;

	@Autowired
	ProfileUtils profileUtils;

	private <T> Response<T> buildResponse(ResultResponse result, T data) {
		Response<T> response = new Response<T>();
		if(result == null) {
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

	@GetMapping(value = "/test/{asset}")
	public @ResponseBody
	Response<?> test(@PathVariable String asset, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("call test dailyWorkPlan with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(200, "OK");
		return buildResponse(result, "OK Response");
	}
	
	@GetMapping(value= {"/listActivities/{asset}", "public/listActivities/{asset}"})
	public @ResponseBody
	Response<?> listActivities(@PathVariable String asset, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("listActivities with asset {}", asset);
		}
		//String UID = JWTUtils.getUid(JWTUtils.getJWTfromHeader(req));
		ResultResponse result = new ResultResponse(200, "OK");
		return buildResponse(result, dailyWorkPlanService.listActivities(asset));
	}
	
	@PostMapping(value= {"/getPlanningByReferenceDate/{asset}", "public/getPlanningByReferenceDate/{asset}"})
	public @ResponseBody
	Response<?> getPlanningByReferenceDate(@PathVariable String asset, @RequestBody(required = true) PlanningFilter data, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("getPlanningByReferenceDate with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, dailyWorkPlanService.getPlanningByReferenceDate(asset, data.getFromDate(), data.getToDate()));
	}
	
	@PostMapping(value= {"/getPlanningById/{asset}", "public/getPlanningById/{asset}"})
	public @ResponseBody
	Response<?> getPlanningById(@PathVariable String asset, @RequestBody(required = true) PlanningFilter data, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("getPlanningById with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, dailyWorkPlanService.getPlanningById(asset, data.getPlanningId()));
	}
	
	@PostMapping(value = {"/saveListActivities/{asset}", "/public/saveListActivities/{asset}"})
    public @ResponseBody
    Response<?> saveListActivities(@PathVariable String asset, @RequestBody PlanningSaveRequest planningSaveRequest, HttpServletRequest request) {
        ResultResponse resultResponse = null;
        try {
        		dailyWorkPlanService.saveListActivities(asset, planningSaveRequest.getPlanning());
                resultResponse = new ResultResponse(codeOK, OK);
                logger.debug("User authorized saveListActivities");

            return buildResponse(resultResponse, null);
        } catch (Exception e) {
            logger.error("Error during call saveListActivities ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
    }
	
	@PostMapping(value = { "/getExportExcel/{asset}", "/public/getExportExcel/{asset}" })
	public @ResponseBody ResponseEntity<?> getMocRegistryExcel(@RequestBody(required = false) PlanningFilter data,
			@PathVariable("asset") String asset, HttpServletRequest request) {
		ByteArrayOutputStream baos = null;

		try {
			baos = dailyWorkPlanService.getExportExcel(asset, data);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
			return ResponseEntity.ok().headers(header).contentLength(baos.size())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new ByteArrayResource(baos.toByteArray()));

		} catch (Exception e) {
			logger.error("could not send excel getExportExcel", e);
			return new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					logger.error("could not close baos", e);
				}
			}

		}
	}
	
	
	@PostMapping(value = { "/getEmptyTemplate/{asset}", "/public/getEmptyTemplate/{asset}" })
	public @ResponseBody ResponseEntity<?> getEmptyTemplate(@RequestBody(required = false) PlanningFilter data,
			@PathVariable("asset") String asset, HttpServletRequest request) {
		ByteArrayOutputStream baos = null;

		try {
			baos = dailyWorkPlanService.getEmptyTemplate(asset);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
			return ResponseEntity.ok().headers(header).contentLength(baos.size())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new ByteArrayResource(baos.toByteArray()));

		} catch (Exception e) {
			logger.error("could not send excel getEmptyTemplate", e);
			return new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					logger.error("could not close baos", e);
				}
			}

		}
	}
	
	@PostMapping(value = {"/copyListActivities/{asset}", "/public/copyListActivities/{asset}"})
    public @ResponseBody
    Response<?> copyListActivities(@PathVariable String asset, @RequestBody PlanningCopy planningCopyRequest, HttpServletRequest request) {
        ResultResponse resultResponse = null;
        try {
        		resultResponse = new ResultResponse(codeOK, OK);
                logger.debug("User authorized copyListActivities");
                return buildResponse(resultResponse, dailyWorkPlanService.copyListActivities(asset, planningCopyRequest.getDestinationDate(), planningCopyRequest.getSourceDate(), planningCopyRequest.getInsertionBy()));
        } catch (Exception e) {
            logger.error("Error during call copyListActivities ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
    }
	
	@RequestMapping(value= {"/getFilterLists/{asset}", "public/getFilterLists/{asset}"})
	public @ResponseBody
	Response<?> getFilterLists(@PathVariable String asset, HttpServletRequest req) {
		try {
			if(logger.isInfoEnabled()) {
				logger.info("getFilterLists with asset {}", asset);
			}
			ResultResponse result = new ResultResponse(codeOK, OK);
			return buildResponse(result, dailyWorkPlanService.getFilterLists(asset));
		} catch (Exception e) {
            logger.error("Error during call getFilterLists ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
	}
	
	@PostMapping(value = {"/getReportData/{asset}", "/public/getReportData/{asset}"})
	public @ResponseBody
    Response<?> getReportData(@PathVariable String asset, @RequestBody ReportFilter reportFilterRequest, HttpServletRequest request) {
		ResultResponse resultResponse = null;
		try {
			 //dailyWorkPlanService.getReportData(asset, reportFilterRequest);
			 resultResponse = new ResultResponse(codeOK, OK);
             logger.debug("User authorized getReportData");
             ReportDto response = dailyWorkPlanService.getReportData(asset, reportFilterRequest);
             
             return buildResponse(resultResponse, response);

             //return buildResponse(resultResponse, null);
		} catch (Exception e) {
            logger.error("Error during call getReportData ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
	}
	
	
	@PostMapping(value= {"/confirmPlanning/{asset}", "public/confirmPlanning/{asset}"})
	public @ResponseBody
	Response<?> confirmPlanning(@PathVariable String asset, @RequestBody(required = true) PlanningFilter data, HttpServletRequest req) {
        ResultResponse resultResponse = null;
        try {
        		dailyWorkPlanService.confirmPlanning(asset, data.getPlanningId(), data.getTipoConferma(), data.getNote(), data.getConfirmationBy());
                resultResponse = new ResultResponse(codeOK, OK);
                logger.debug("User authorized confirmPlanning");
            return buildResponse(resultResponse, null);
        } catch (Exception e) {
            logger.error("Error during call confirmPlanning ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
    }
	
	@PostMapping(value= {"/existingPlanningByReferenceDate/{asset}", "public/existingPlanningByReferenceDate/{asset}"})
	public @ResponseBody
	Response<?> existingPlanningByReferenceDate(@PathVariable String asset, @RequestBody(required = true) PlanningFilter data, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("existingPlanningByReferenceDate with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, dailyWorkPlanService.existingPlanningByReferenceDate(asset, data.getFromDate(), data.getToDate()));
	}
	
	@RequestMapping(value = {"/uploadTemplateFile/{asset}","/public/uploadTemplateFile/{asset}"}, method = RequestMethod.POST)
	public @ResponseBody
	Response<?> uploadTemplateFile(@PathVariable("asset") String asset, @RequestParam(required = true) MultipartFile file,
			 String referenceDate, String uid, HttpServletRequest request) {
		if(logger.isInfoEnabled()) {
			logger.info("uploadTemplateFile with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, dailyWorkPlanService.uploadTemplateFile(asset, file, referenceDate, uid));
	}
	
	@PostMapping(value= {"/getRolesByUid/{asset}", "public/getRolesByUid/{asset}"})
	public @ResponseBody
	Response<?> getRolesByUid(@PathVariable String asset, @RequestBody(required = true) UserFilter data, HttpServletRequest req) {
		if(logger.isInfoEnabled()) {
			logger.info("getRolesByUid with asset {}", asset);
		}
		ResultResponse result = new ResultResponse(codeOK, OK);
		return buildResponse(result, dailyWorkPlanService.getRolesByUid(asset, data.getUid()));
	}
	
	@RequestMapping(value= {"/getCardInfo/{asset}", "public/getCardInfo/{asset}"})
	public @ResponseBody
	Response<?> getCardInfo(@PathVariable String asset, HttpServletRequest req) {
		try {
			if(logger.isInfoEnabled()) {
				logger.info("getCardInfo with asset {}", asset);
			}
			ResultResponse result = new ResultResponse(codeOK, OK);
			return buildResponse(result, dailyWorkPlanService.getCardInfo(asset));
		} catch (Exception e) {
            logger.error("Error during call getCardInfo ", e);
            return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
        }
	}

}
