package com.eni.enhop.be.shifthandoverlogbook.mapper;

import org.mapstruct.Mapper;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPageSummary;

@Mapper
public interface LogbookPageSummaryMapper {

	LogbookPageSummary toLogbookSummary(LogbookPage logbookPage);
}
