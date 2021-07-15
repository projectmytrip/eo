package com.eni.ioc.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eni.ioc.scheduler.JobUtils;

/*
 * Do not delete this controller.
 * 
 * */
@RestController
@RequestMapping("/retrievejob")
public class CrontabRetrieverJobController {
	
	@Value("${crontab.cloudera.retriever}")
	private String crontabClouderaRetriever;
	
	private static final Logger logger = LoggerFactory.getLogger(CrontabRetrieverJobController.class);
	
    @RequestMapping(value = "/ping",method = RequestMethod.GET)
	public String ping() {
		logger.debug(" -- Controller ping Called-- ");
		return "OK";
	}
    
    
    /*@RequestMapping(value = "/start",method = RequestMethod.GET)
   	public String startScheduler() {
    	logger.debug(" -- Controller start scheduler Called-- ");
		Scheduler scheduler = ApplicationMain.scheduler;
		String messageResult= "";
    	try {
			if (scheduler!= null && scheduler.isStarted()){
				 messageResult= "Job Retriever-PersistService already running";
				logger.debug("Controller REST: "+messageResult);
				return messageResult ; 		
			}else{
				logger.debug("starting new Sender Job...");
				ApplicationMain.scheduler= SchedulerUtils.createScheduler();
				SchedulerUtils.startRetrieveH2sPredictiveProbabilitiesJob(crontabClouderaRetriever , scheduler);
				SchedulerUtils.startRetrieveH2sPredictiveImpactsJob(crontabClouderaRetriever , scheduler);
				 messageResult = "job Started!";
				logger.debug("Controller REST: "+ messageResult);
				return messageResult;
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageResult;

    }*/
    
    @RequestMapping(value = "/testfile",method = RequestMethod.GET)
   	public String testFile() {
    	logger.debug(" -- Controller start testfile Called-- ");
    	try {
    		String result = "";
    		BufferedReader br = null;
    		FileReader fr = null;
    		try {
    			fr = new FileReader("/app/krb5.conf");
    			br = new BufferedReader(fr);
    			String sCurrentLine;
    			while ((sCurrentLine = br.readLine()) != null) {
    				result += sCurrentLine;
    			}
    		} catch (IOException e) {
    			logger.error("IOException", e);
    		} finally {
    			try {
    				if (br != null)
    					br.close();
    				if (fr != null)
    				fr.close();
    			} catch (IOException ex) {
    				logger.error("IOException", ex);
    			}
    		}
    		
    		return result;
		} catch (Exception e) {
			logger.error("Error during testfile", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveProbabilities",method = RequestMethod.GET)
   	public String retrieveProbabilities() {
    	logger.debug(" -- Controller start retrieveProbabilities Called-- ");
    	try {
			return JobUtils.executeProbabilities();
		} catch (Exception e) {
			logger.error("Error during retrieveProbabilities", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveImpacts",method = RequestMethod.GET)
   	public String retrieveImpacts() {
    	logger.debug(" -- Controller start retrieveImpacts Called-- ");
    	try {
			return JobUtils.executeImpact();
		} catch (Exception e) {
			logger.error("Error during retrieveImpacts", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveAverages",method = RequestMethod.GET)
   	public String retrieveAverages() {
    	logger.debug(" -- Controller start retrieveImpacts Called-- ");
    	try {
			return JobUtils.executeAverages();
		} catch (Exception e) {
			logger.error("Error during retrieveImpacts", e);
			return "Error";
		}
    }
	
    @RequestMapping(value = "/retrieveAnomaly",method = RequestMethod.GET)
   	public String retrieveAnomaly() {
    	logger.debug(" -- Controller start retrieveAnomaly Called-- ");
    	try {
			return "E13: " + JobUtils.executeAnomaly("E13") + ", E14: " + JobUtils.executeAnomaly("E14") + " , E15: " + JobUtils.executeAnomaly("E15");
		} catch (Exception e) {
			logger.error("Error during retrieveAnomaly", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveEvents",method = RequestMethod.GET)
   	public String retrieveEvents() {
    	logger.debug(" -- Controller start retrieveEvents Called-- ");
    	try {
			return "E13: " + JobUtils.executeFlaringEvent("E13") + ", E14: " + JobUtils.executeFlaringEvent("E14") + " , E15: " + JobUtils.executeFlaringEvent("E15");
		} catch (Exception e) {
			logger.error("Error during retrieveEvents", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveTda",method = RequestMethod.GET)
   	public String retrieveTda() {
    	logger.debug(" -- Controller start retrieveTda Called-- ");
    	try {
			return JobUtils.executeRootCause();
		} catch (Exception e) {
			logger.error("Error during retrieveTda", e);
			return "Error";
		}
    }
    
    @RequestMapping(value = "/retrieveProbabilitiesFlar",method = RequestMethod.GET)
   	public String retrieveProbabilitiesFlar() {
    	logger.debug(" -- Controller start retrieveProbabilitiesFlar Called-- ");
    	try {
			return "E13: " + JobUtils.executeProbabilitiesFlar("E13") + ", E14: " + JobUtils.executeProbabilitiesFlar("E14") + " , E15: " + JobUtils.executeProbabilitiesFlar("E15");
		} catch (Exception e) {
			logger.error("Error during retrieveProbabilitiesFlar", e);
			return "Error";
		}
    }
	
}
