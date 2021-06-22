package com.eni.enhop.be.shifthandoverlogbook.model;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eni.enhop.be.shifthandoverlogbook.converter.LocaleConverter;

@Entity
@Table(name = "email_templates")
public class EmailTemplate
{
	@Id
	@Column(name = "id_email_template")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	id;
	@Column
	private String	name;
	@Column
	@Convert(converter = LocaleConverter.class)
	private Locale	locale;
	@Column
	private String	subject;
	@Column
	private String	body;
	@Column(name = "is_default")
	private boolean	isDefault;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public boolean isDefault()
	{
		return isDefault;
	}

	public void setDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

}
