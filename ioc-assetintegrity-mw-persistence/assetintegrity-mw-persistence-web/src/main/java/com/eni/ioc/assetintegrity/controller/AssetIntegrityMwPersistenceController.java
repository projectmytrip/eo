package com.eni.ioc.assetintegrity.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.eni.ioc.assetintegrity.api.DERegistry;
import com.eni.ioc.assetintegrity.common.Response;
import com.eni.ioc.assetintegrity.common.ResultResponse;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionBacteriaDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCNDItem;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionKpiDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionPiggingDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.CorrosionProtectionDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.IntegrityOperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.JacketedPipesDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.OperatingWindowsDto;
import com.eni.ioc.assetintegrity.dto.storedataservice.SegnaleCriticoApi;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfMOCRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreCriticalSignalsWfRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceEVPMS;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.StoreDataServiceWellRequest;
import com.eni.ioc.assetintegrity.dto.storedataservice.ewpBarrierPanel;
import com.eni.ioc.assetintegrity.entities.RegistroMoc;
import com.eni.ioc.assetintegrity.service.AssetIntegrityService;

@RestController
@RequestMapping("/assetintegrity")
public class AssetIntegrityMwPersistenceController {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityMwPersistenceController.class);

	@Autowired
	AssetIntegrityService assetIntegrityService;

	@GetMapping(value = { "/test" })
	public @ResponseBody
	Response<?> test(HttpServletRequest request) {
		logger.debug("test");

		try {
			assetIntegrityService.updateCriticalSignalsActivationDates();
			ResultResponse result = new ResultResponse(200, "OK");
			logger.info("Persist OK");
			return buildResponse(result, "OK");
		} catch (Exception e) {
			logger.error("Error during call test ", e);
			return buildResponse(new ResultResponse(501, "KO", e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/badJacketedPipesMail/{asset}" })
	public @ResponseBody
	Response<?> badJacketedPipesMail(HttpServletRequest request, @PathVariable(value = "asset") String asset) {
		logger.debug("badJacketedPipesMail");

		try {
			assetIntegrityService.sendBadJackedPipesMail(asset);
			ResultResponse result = new ResultResponse(200, "OK");
			logger.info("badJacketedPipesMail OK");
			return buildResponse(result, "OK");
		} catch (Exception e) {
			logger.error("Error during badJacketedPipesMail ", e);
			return buildResponse(new ResultResponse(501, "KO", e.getMessage()), "");
		}
	}

	//PER LA DE!
	@PostMapping(value = "/updateSignalsMOC/{asset}")
	public @ResponseBody
	Response<?> updateSignalsMOC(@RequestBody(required = false) StoreCriticalSignalsWfRequest input,
			@PathVariable(value = "asset") String asset, HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.updateSignalsMOC_DE(input, asset);
			result = new ResultResponse(200, "OK");
			logger.info("updateSignalsMOC Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling updateSignalsMOC ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/insertUpdateSignalsMOC/{asset}")
	public @ResponseBody
	Response<?> insertUpdateSignalsMOC(@RequestBody(required = false) StoreCriticalSignalsWfMOCRequest input,
			@PathVariable(value = "asset") String asset, HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertUpdateSignalsMOC(input, asset);
			result = new ResultResponse(200, "OK");
			logger.info("insertUpdateSignalsMOC Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling insertUpdateSignalsMOC ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/DERegistry")
	public @ResponseBody
	Response<?> DERegistry(@RequestBody(required = true) DERegistry input, HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertRegistroDe(input);
			result = new ResultResponse(200, "OK");
			logger.info("RegistroMoc Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling RegistroMoc ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/MocRegistry")
	public @ResponseBody
	Response<?> MocRegistry(@RequestBody(required = true) RegistroMoc input, HttpServletRequest request) {
		ResultResponse result;
		try {
			logger.info(input.toString());
			assetIntegrityService.insertRegistroMoc(input);
			result = new ResultResponse(200, "OK");
			logger.info("RegistroMoc Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling RegistroMoc ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/storedataserviceSegnaleCritico")
	public @ResponseBody
	Response<?> storeDataService(@RequestBody(required = false) StoreDataServiceRequest<SegnaleCriticoApi> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertSegnaliCritici(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceSegnaleCritico Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceSegnaleCritico ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@GetMapping(value = "/updateSegnaleCritico/{asset}")
	public @ResponseBody
	Response<?> updateSegnaleCritico(@PathVariable(value = "asset") String asset, HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.updateCriticalSignalsActivationDates();
			assetIntegrityService.updateRegistroDe();
			result = new ResultResponse(200, "OK");
			logger.info("updateSegnaleCritico OK");
		} catch (Exception e) {
			logger.error("Error in calling updateSegnaleCritico ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/storedataserviceWell")
	public @ResponseBody
	Response<?> storeDataServiceWell(@RequestBody(required = false) StoreDataServiceWellRequest input,
			HttpServletRequest request) {
		logger.info("Received POST on /storedataserviceWell");
		ResultResponse result;
		try {
			assetIntegrityService.insertWellAlarm(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceWell Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceWell ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceWeekWell")
	public @ResponseBody
	Response<?> storeDataServiceWeekWell(@RequestBody(required = false) StoreDataServiceWellRequest input,
			HttpServletRequest request) {
		logger.info("Received POST on /storedataserviceWeekWell");
		ResultResponse result;
		try {
			assetIntegrityService.insertWeekWellAlarm(input);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceWell Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceWeekWell ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceSap")
	public @ResponseBody
	Response<?> storeDataServiceSap(@RequestParam(name = "file701") MultipartFile file701,
			@RequestParam(name = "file801") MultipartFile file801) {
		ResultResponse result;
		try {
			assetIntegrityService.insertSapDocument(file701 != null ? file701.getBytes() : null,
					file801 != null ? file801.getBytes() : null);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceSap Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storeDataServiceSap ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceBacklogTotalCount")
	public @ResponseBody
	Response<?> storeDataServiceBacklogTotalCount(@RequestParam(name = "fileTC70") MultipartFile file) {
		ResultResponse result;

		try {
			assetIntegrityService.insertBacklogTotalCount(file != null ? file.getBytes() : null);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceBacklogTotalCount Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storeDataServiceBacklogTotalCount ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceOperatingWindowKpi")
	public @ResponseBody
	Response<?> storeDataServiceOperatingWindowKpi(
			@RequestBody(required = false) StoreDataServiceRequest<OperatingWindowsDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertOperatingWindow(input);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceOperatingWindowKpi Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storeDataServiceOperatingWindowKpi ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceIntegrityOperatingWindowKpi")
	public @ResponseBody
	Response<?> storedataserviceIntegrityOperatingWindowKpi(
			@RequestBody(required = false) StoreDataServiceRequest<IntegrityOperatingWindowsDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertIntegrityOperatingWindow(input);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceIntegrityOperatingWindowKpi Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storeDataServiceIntegrityOperatingWindowKpi ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionKpi")
	public @ResponseBody
	Response<?> storeDataServiceCorrosionKpi(
			@RequestBody(required = false) StoreDataServiceRequest<CorrosionKpiDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionKpi(input);
			result = new ResultResponse(200, "OK");
			logger.info("storeDataServiceCorrosionKpi Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storeDataServiceCorrosionKpi ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionBacteria")
	public @ResponseBody
	Response<?> storedataserviceCorrosionBacteria(
			@RequestBody(required = false) StoreDataServiceRequest<CorrosionBacteriaDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionBacteria(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceCorrosionBacteria Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceCorrosionBacteria ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionCND")
	public @ResponseBody
	Response<?> storedataserviceCND(@RequestBody(required = false) StoreDataServiceRequest<CorrosionCNDItem> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionCND(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceCorrosionCND persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceCND ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionCoupon/{tab}")
	public @ResponseBody
	Response<?> storedataserviceCoupon(@RequestBody(required = false) StoreDataServiceRequest<CorrosionCouponDto> input,
			HttpServletRequest request, @PathVariable(name = "tab") String tab) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionCoupon(input, tab);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceCoupon Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceCoupon ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionProtection")
	public @ResponseBody
	Response<?> storedataserviceCorrosionProtection(
			@RequestBody(required = false) StoreDataServiceRequest<CorrosionProtectionDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionProtection(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceCorrosionProtection Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceCorrosionProtection ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceCorrosionPigging")
	public @ResponseBody
	Response<?> storedataserviceCorrosionPigging(
			@RequestBody(required = false) StoreDataServiceRequest<CorrosionPiggingDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertCorrosionPigging(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceCorrosionPigging Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceCorrosionPigging ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storeCriticalSignalsWf")
	public @ResponseBody
	Response<?> storeCriticalSignalsWf(@RequestBody StoreCriticalSignalsWfRequest input, HttpServletRequest request) {
		ResultResponse result;
		try {
			boolean res = assetIntegrityService.insertSegnaliCriticiWf(input);
			result = new ResultResponse(200, res + "");
			logger.info("storeCriticalSignalsWf Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceSegnaleCritico ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}

		return buildResponse(result, "OK");

	}

	@PostMapping(value = "/storedataserviceEVPMS")
	public @ResponseBody
	Response<?> storedataserviceEVPMS(@RequestBody(required = false) StoreDataServiceEVPMS input) {
		ResultResponse result;
		try {
			assetIntegrityService.insertEVPMS(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceEVPMS Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceEVPMS ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceJacketedPipes")
	public @ResponseBody
	Response<?> storedataserviceJacketedPipes(
			@RequestBody(required = false) StoreDataServiceRequest<JacketedPipesDto> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertJacketedPipes(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceJacketedPipes Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceJacketedPipes ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

	@PostMapping(value = "/storedataserviceEWP")
	public @ResponseBody
	Response<?> storedataserviceEWP(@RequestBody(required = false) StoreDataServiceRequest<ewpBarrierPanel> input,
			HttpServletRequest request) {
		ResultResponse result;
		try {
			assetIntegrityService.insertEWP(input);
			result = new ResultResponse(200, "OK");
			logger.info("storedataserviceEWP Persist OK");
		} catch (Exception e) {
			logger.error("Error in calling storedataserviceEWP ", e);
			result = new ResultResponse(501, "KO", e.getMessage());
			return buildResponse(result, "");
		}
		return buildResponse(result, "OK");
	}

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

}
