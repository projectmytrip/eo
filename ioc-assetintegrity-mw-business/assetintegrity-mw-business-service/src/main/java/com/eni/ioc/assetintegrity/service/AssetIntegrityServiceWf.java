package com.eni.ioc.assetintegrity.service;

import java.util.List;

import com.eni.ioc.assetintegrity.api.OpenWorkflowCriticalSignalsInput;
import com.eni.ioc.assetintegrity.api.RequestModificationInput;
import com.eni.ioc.assetintegrity.dto.CheckSignalsWF;
import com.eni.ioc.assetintegrity.enitities.CriticalSignalDto;

public interface AssetIntegrityServiceWf {

	void openWorkflowCriticalSignals(OpenWorkflowCriticalSignalsInput input, String asset, String user, String uid)  throws Exception;
	void requestModification(RequestModificationInput input, String asset, String user, String uid)  throws Exception;
	void updateSignalMoc(CriticalSignalDto input, String asset, String uid)  throws Exception;
	void openCriticalSignalsWaringWF(String asset)  throws Exception;
	CheckSignalsWF checkSignalsStatus(String asset, String wfId) throws Exception;
	List<CriticalSignalDto> pollingMOC(List<String> input, String asset);

}
