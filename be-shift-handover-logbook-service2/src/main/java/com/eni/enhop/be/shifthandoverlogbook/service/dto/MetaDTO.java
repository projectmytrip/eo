package com.eni.enhop.be.shifthandoverlogbook.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.eni.enhop.be.common.core.bean.PrintableBean;

public class MetaDTO extends PrintableBean
{
	private static final long	serialVersionUID	= 6052612473037585573L;

	@JsonProperty("meta_id")
	private String				metaId;

	@JsonProperty("meta_type")
	private String				metaType;

	public String getMetaId()
	{
		return metaId;
	}

	public void setMetaId(String metaId)
	{
		this.metaId = metaId;
	}

	public String getMetaType()
	{
		return metaType;
	}

	public void setMetaType(String metaType)
	{
		this.metaType = metaType;
	}
}
