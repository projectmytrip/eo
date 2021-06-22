package com.eni.enhop.be.shifthandoverlogbook.converter;

import java.util.Locale;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocaleConverter implements AttributeConverter<Locale, String>
{

	@Override
	public String convertToDatabaseColumn(Locale locale)
	{
		if (locale != null)
		{
			return locale.getLanguage();
		}
		return null;
	}

	@Override
	public Locale convertToEntityAttribute(String languageTag)
	{
		if (languageTag != null && !languageTag.isEmpty())
		{
			return Locale.forLanguageTag(languageTag);
		}
		return null;
	}
}
