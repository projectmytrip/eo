package com.eni.enhop.be.shifthandoverlogbook.service;

import javax.servlet.http.HttpServletRequest;

import com.eni.enhop.be.common.core.bean.FileDownloadBean;
import com.eni.enhop.be.files.dto.FileMetaDTO;
import com.eni.enhop.be.shifthandoverlogbook.exception.GenericException;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.service.dto.LogbookPdfRequestDTO;

public interface LogbookFileService {
	
	FileMetaDTO generatePdf(LogbookPdfRequestDTO dto, String userLang, String type);

	public FileDownloadBean download(String metaId) throws GenericException;

	FileMetaDTO prepareFile(LogbookPage logbookPage, HttpServletRequest request);

	void sendEmailToWhitelist(LogbookPage logbookPage, String whitelist, HttpServletRequest request);
}
