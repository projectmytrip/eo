package com.eni.ioc.assetintegrity.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.assetintegrity.api.DERegistryFilter;
import com.eni.ioc.assetintegrity.api.MocRegistryInput;
import com.eni.ioc.assetintegrity.common.Response;
import com.eni.ioc.assetintegrity.common.ResultResponse;
import com.eni.ioc.assetintegrity.dto.BacklogOperationalKpi;
import com.eni.ioc.assetintegrity.dto.CorrosionCNDElement;
import com.eni.ioc.assetintegrity.dto.CorrosionCouponDto;
import com.eni.ioc.assetintegrity.dto.CriticalSignalsOperationalKpi;
import com.eni.ioc.assetintegrity.dto.EWPDto;
import com.eni.ioc.assetintegrity.enitities.AreasDto;
import com.eni.ioc.assetintegrity.enitities.CountCriticalDto;
import com.eni.ioc.assetintegrity.enitities.CriticalCount;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;
import com.eni.ioc.assetintegrity.enitities.DetailListDto;
import com.eni.ioc.assetintegrity.entities.Backlog;
import com.eni.ioc.assetintegrity.entities.CriticalSignal;
import com.eni.ioc.assetintegrity.entities.TemplateFile;
import com.eni.ioc.assetintegrity.entities.WellAlarm;
import com.eni.ioc.assetintegrity.service.AssetIntegrityService;
import com.eni.ioc.assetintegrity.service.ExcelService;

@RestController
@RequestMapping("/assetintegrity")
public class AssetIntegrityMwBusinessController {

	private static final Logger logger = LoggerFactory.getLogger(AssetIntegrityMwBusinessController.class);
	private long codeOK = 200;
	private long codeKO = 501;
	private static final String OK = "OK";
	private static final String KO = "KO";

	@Autowired
	AssetIntegrityService assetIntegrityService;

	@Autowired
	ExcelService excelService;

	@GetMapping(value = { "/getEWPAreas/{asset}/{parentMap}/{parentArea}",
			"/public/getEWPAreas/{asset}/{parentMap}/{parentArea}" })
	public @ResponseBody Response<?> getEWPAreas(@PathVariable("asset") String asset,
			@PathVariable("parentMap") String parentMap, @PathVariable("parentArea") String parentArea,
			HttpServletRequest request) {

		AreasDto l = null;

		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.getEWPAreas(asset, parentMap, parentArea);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getAreas");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getAreas ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getEWPTags/{asset}/{parentMap}", "/public/getEWPTags/{asset}/{parentMap}" })
	public @ResponseBody Response<?> getEWPTags(@PathVariable("asset") String asset,
			@PathVariable("parentMap") String parentMap, HttpServletRequest request) {

		ResultResponse resultResponse = null;

		try {


			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getEWPTags");

			return buildResponse(resultResponse, assetIntegrityService.getEWPTags(asset, parentMap));
		} catch (Exception e) {
			logger.error("Error during call getEWPTags ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getEWPSubAreas/{asset}/{parentArea}", "/public/getEWPSubAreas/{asset}/{parentArea}" })
	public @ResponseBody Response<?> getEWPSubAreas(@PathVariable("asset") String asset,
			@PathVariable("parentArea") String parentArea, HttpServletRequest request) {

		AreasDto l = null;
		ResultResponse resultResponse = null;

		try {
			l = assetIntegrityService.getEWPSubAreas(asset, parentArea);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getEWPSubAreas");
			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getEWPSubAreas ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getEWPCounts/{asset}", "/public/getEWPCounts/{asset}" })
	public @ResponseBody Response<?> getEWPCounts(@PathVariable("asset") String asset, HttpServletRequest request) {

		ResultResponse resultResponse = null;

		try {
			resultResponse = new ResultResponse(codeOK, OK);

			logger.debug("User authorized to getEWPCounts");

			return buildResponse(resultResponse, assetIntegrityService.getEWPCounts(asset));
		} catch (Exception e) {
			logger.error("Error during call getEWPCounts ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getEwp/{asset}/{area}", "/public/getEwp/{asset}/{area}" })
	public @ResponseBody Response<?> getEwp(HttpServletRequest request, @PathVariable("asset") String asset,
			@PathVariable("area") String area) {
		logger.debug("getEwp");
		List<EWPDto> result = null;

		ResultResponse resultResponse = null;

		try {
			result = assetIntegrityService.getEwp(asset, area, parentArea(area, asset));
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get getEwp");

			return buildResponse(resultResponse, result);
		} catch (Exception e) {
			logger.error("Error during call getEwp ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@PostMapping(value = { "/getDERegistry/{asset}", "/public/getDERegistry/{asset}" })
	public @ResponseBody Response<?> getDERegistry(@RequestBody(required = false) DERegistryFilter data,
			@PathVariable("asset") String asset, HttpServletRequest request) {

		logger.debug("User authorized to getDERegistry");
		ResultResponse resultResponse = null;

		try {
			resultResponse = new ResultResponse(codeOK, OK);
			return buildResponse(resultResponse, assetIntegrityService.getRegistroDE(data, asset));
		} catch (Exception e) {
			logger.error("Error during call getDERegistry ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@PostMapping(value = { "/getMocRegistry/{asset}", "/public/getMocRegistry/{asset}" })
	public @ResponseBody Response<?> getMocRegistry(@RequestBody(required = false) MocRegistryInput data,
			@PathVariable("asset") String asset, HttpServletRequest request) {

		logger.debug("User authorized to getMocRegistry");
		ResultResponse resultResponse = null;

		try {
			resultResponse = new ResultResponse(codeOK, OK);
			return buildResponse(resultResponse, assetIntegrityService.getRegistroMoc(data));
		} catch (Exception e) {
			logger.error("Error during call getMocRegistry ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@PostMapping(value = { "/getMocRegistryExcel/{asset}", "/public/getMocRegistryExcel/{asset}" })
	public @ResponseBody ResponseEntity<?> getMocRegistryExcel(@RequestBody(required = false) MocRegistryInput data,
			@PathVariable("asset") String asset, HttpServletRequest request) {
		ByteArrayOutputStream baos = null;

		try {
			baos = assetIntegrityService.generateMOCRegistryExcel(data, asset);
			// baos = excelService.generateExcel("Registro MoC", data, asset,
			// genClass, items);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
			return ResponseEntity.ok().headers(header).contentLength(baos.size())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new ByteArrayResource(baos.toByteArray()));

		} catch (Exception e) {
			logger.error("could not send excel getMocRegistryExcel", e);
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

	@PostMapping(value = { "/getDERegistryExcel/{asset}", "/public/getDERegistryExcel/{asset}" })
	public @ResponseBody ResponseEntity<?> getDERegistryExcel(@RequestBody(required = false) DERegistryFilter data,
			@PathVariable("asset") String asset, HttpServletRequest request) {
		ByteArrayOutputStream baos = null;

		try {
			baos = assetIntegrityService.generateDERegistryExcel(data, asset);
			// baos = excelService.generateExcel("Registro D/E", data, asset,
			// genClass, items);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
			return ResponseEntity.ok().headers(header).contentLength(baos.size())
					.contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(new ByteArrayResource(baos.toByteArray()));

		} catch (Exception e) {
			logger.error("could not send excel getDERegistryExcel", e);
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

	@GetMapping(value = { "/countCriticals/{asset}/{table}", "/public/countCriticals/{asset}/{table}" })
	public @ResponseBody Response<?> countCriticals(@PathVariable("table") String table,
			@PathVariable("asset") String asset, HttpServletRequest request) {

		CountCriticalDto l = null;
		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.countCriticalHigh(table, asset);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to countCriticals");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call countCriticals ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getDashboard/{asset}", "/public/getDashboard/{asset}" })
	public @ResponseBody Response<?> getDashboard(@PathVariable("asset") String asset, HttpServletRequest request) {

		CriticalCount l = null;
		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.getDashboard(asset);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getDashboard");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getDashboard ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getAreas/{asset}/{id}/{tab}", "/public/getAreas/{asset}/{id}/{tab}" })
	public @ResponseBody Response<?> getAreas(@PathVariable("asset") String asset, @PathVariable("id") Long id,
			@PathVariable("tab") String tab, HttpServletRequest request) {

		AreasDto l = null;

		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.getAreas(asset, id, tab);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getAreas");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getAreas ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getDetails/{asset}/{table}", "/public/getDetails/{asset}/{table}" })
	public @ResponseBody Response<?> getDetails(@PathVariable("table") String table,
			@PathVariable("asset") String asset, HttpServletRequest request) {

		DetailListDto l = null;

		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.showDetails(table, asset);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getDetails");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getDetails ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getDetailsByDate/{asset}/{table}", "/public/getDetailsByDate/{asset}/{table}" })
	public @ResponseBody Response<?> getDetailsByDate(@PathVariable("table") String table,
			@PathVariable("asset") String asset, @RequestParam String from,
			@RequestParam String to, HttpServletRequest request) {

		DetailListDto l = null;

		ResultResponse resultResponse = null;

		try {

			l = assetIntegrityService.showDetailsByDate(table, asset, from, to);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getDetailsByDate");

			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call getDetailsByDate ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/backlogList/{asset}", "/backlogList/{asset}/{area}", "/public/backlogList/{asset}",
			"/public/backlogList/{asset}/{area}" })
	public @ResponseBody Response<?> backlogList(@PathVariable("asset") String asset,
			@PathVariable("area") Optional<String> area, HttpServletRequest request) {
		logger.debug("start");
		List<Backlog> l = null;

		ResultResponse resultResponse = null;

		try {
			if (area.isPresent()) {
				l = assetIntegrityService.backloglist(asset, area.get(), parentArea(area.get(), asset));
			} else {
				l = assetIntegrityService.backloglist(asset, null, null);
			}

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get backlogList");

			logger.debug("end");
			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call backlogList ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/criticalSignal/{asset}", "/criticalSignal/{asset}/{area}", "/public/criticalSignal/{asset}",
			"/public/criticalSignal/{asset}/{area}" })
	public @ResponseBody Response<?> criticalSignal(HttpServletRequest request, @PathVariable("asset") String asset,
			@PathVariable("area") Optional<String> area) {

		logger.debug("criticalSignal");
		List<CriticalSignalDto> l = null;

		ResultResponse resultResponse = null;

		try {
			if (area.isPresent()) {
				l = assetIntegrityService.signcriticilistDto(area.get(), parentArea(area.get(), asset));
			} else {
				l = assetIntegrityService.signcriticilistDto(null, null);
			}

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get criticalSignal");

			logger.debug("end");
			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call criticalSignal ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getSignals/{asset}", "/public/getSignals/{asset}" })
	public @ResponseBody Response<?> getSignals(HttpServletRequest request, @PathVariable("asset") String asset,
			@RequestParam(name = "active", required = false) Integer active) {
		logger.debug("criticalSignal");

		List<CriticalSignal> l = null;

		ResultResponse resultResponse = null;

		try {
			l = assetIntegrityService.getAllSignals(asset, active);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get criticalSignal");

			logger.debug("end");
			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call criticalSignal ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getAllSignals/{asset}", "/public/getAllSignals/{asset}" })
	public @ResponseBody Response<?> getAllSignals(HttpServletRequest request, @PathVariable("asset") String asset) {
		logger.debug("criticalSignal");

		List<CriticalSignal> l = null;

		ResultResponse resultResponse = null;

		try {
			l = assetIntegrityService.getAllSignals(asset, null, true);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get criticalSignal");

			logger.debug("end");
			return buildResponse(resultResponse, l);
		} catch (Exception e) {
			logger.error("Error during call criticalSignal ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/getWellAlarm/{asset}", "/public/getWellAlarm/{asset}" })
	public @ResponseBody Response<?> getWellAlarm(HttpServletRequest request, @PathVariable("asset") String asset) {
		logger.debug("getWellAlarm");
		List<WellAlarm> result = null;

		ResultResponse resultResponse = null;

		try {
			result = assetIntegrityService.getWellAlarms(asset);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get getWellAlarm");

			return buildResponse(resultResponse, result);
		} catch (Exception e) {
			logger.error("Error during call wellAlarm ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/operationalKpi/CriticalSignals/{asset}", "/public/operationalKpi/CriticalSignals/{asset}" })
	public @ResponseBody Response<?> getCriticalSignalsKpi(HttpServletRequest request,
			@PathVariable("asset") String asset) {
		logger.debug("start operationalKpi/CriticalSignals");
		CriticalSignalsOperationalKpi result = null;

		ResultResponse resultResponse = null;

		try {
			result = assetIntegrityService.getCriticalSignalsKpi(asset);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get operationalKpi");

			return buildResponse(resultResponse, result);
		} catch (Exception e) {
			logger.error("Error during call operationalKpi ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/operationalKpi/Backlog/{asset}", "/public/operationalKpi/Backlog/{asset}" })
	public @ResponseBody Response<?> getBacklogKpi(HttpServletRequest request, @PathVariable("asset") String asset) {
		logger.debug("start operationalKpi/Backlog");
		BacklogOperationalKpi count = null;
		ResultResponse resultResponse = null;

		try {
			count = assetIntegrityService.getBacklogKpi(asset);
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get operationalKpi/Backlog");

			logger.debug("end");
			return buildResponse(resultResponse, count);
		} catch (Exception e) {
			logger.error("Error during call operationalKpi/Backlog ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/corrosion/CND/{asset}/{area}/{element}", "/public/corrosion/CND/{asset}/{area}/{element}" })
	public @ResponseBody Response<?> getCorrosionCND(HttpServletRequest request, @PathVariable("asset") String asset,
			@PathVariable("element") String element, @RequestParam(name = "year", required = false) String year,
			@PathVariable("area") String area) {

		logger.debug("start getCorrosionCND");
		List<CorrosionCNDElement> result = null;
		ResultResponse resultResponse = null;

		try {

			result = assetIntegrityService.getCorrosionCND(asset, element, year, area, parentArea(area, asset));
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get getCorrosionCND");
			logger.debug("end");
			return buildResponse(resultResponse, result);
		} catch (Exception e) {
			logger.error("Error during call getCorrosionCND ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = { "/corrosion/coupon/{asset}/{area}/{code}", "/public/corrosion/coupon/{asset}/{area}/{code}" })
	public @ResponseBody Response<?> getCorrosionCoupon(HttpServletRequest request, @PathVariable("asset") String asset,
			@PathVariable("code") String code, @RequestParam(name = "year", required = false) String year,
			@PathVariable("area") String area) {

		logger.debug("start getCorrosionCoupon");
		List<CorrosionCouponDto> result = null;
		ResultResponse resultResponse = null;

		try {
			result = assetIntegrityService.getCorrosionCouponElements(asset, code, year, area, parentArea(area, asset));
			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to get getCorrosionCoupon");
			logger.debug("end");
			return buildResponse(resultResponse, result);
		} catch (Exception e) {
			logger.error("Error during call getCorrosionCoupon ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value={"/getTemplate/{asset}/{code}", "/public/getTemplate/{asset}/{code}"})
	public @ResponseBody Response<?>  getTemplate(@PathVariable("asset") String asset,
			@PathVariable("code") String code, HttpServletRequest request) throws Exception{

		ResultResponse resultResponse = null;
		try {

			TemplateFile template = assetIntegrityService.getTemplate(code, asset);

			resultResponse = new ResultResponse(codeOK, OK);
			logger.debug("User authorized to getTemplate");

			return buildResponse(resultResponse, template);

		} catch (Exception e) {
			logger.error("Error during call getTemplate ", e);
			return buildResponse(new ResultResponse(codeKO, KO, e.getMessage()), "");
		}
	}

	@GetMapping(value = "/ping")
	public @ResponseBody Response<?> ping(HttpServletRequest request) {
		ResultResponse result = new ResultResponse(200, "");
		return buildResponse(result, "OK");
	}

	private String parentArea(String subArea, String asset) {
		return assetIntegrityService.parentArea(subArea, asset);
	}

	private <T> Response<T> buildResponse(ResultResponse result, T data) {
		Response<T> response = new Response<>();
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