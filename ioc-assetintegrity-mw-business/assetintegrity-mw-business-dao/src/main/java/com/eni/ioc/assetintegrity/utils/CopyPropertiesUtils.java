package com.eni.ioc.assetintegrity.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyPropertiesUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CopyPropertiesUtils.class);

	private CopyPropertiesUtils() {
	}

	/**
	 * Questo metodo consente la copia dei valori da due classe generiche
	 * separate in cui: - combaciano i nomi dell'attributo - combaciano i tipi
	 * dell'attributo - l'attributo è nella lista passata in input
	 * 
	 * Il metodo fa questo matching anche per eventuali classi Primary Key
	 * presenti all'interno del file di input
	 * 
	 * @param input
	 * @param output
	 * @return
	 * @throws TechnicalException
	 */
	public static <T> T copyProperties(T from, T to, String[] propertiesToCopy) {
		String currentMethodName;
		String returnType;
		for (Method currentMethodInput : from.getClass().getMethods()) {
			currentMethodName = currentMethodInput.getName();
			returnType = currentMethodInput.getReturnType().getSimpleName();
			try {
				if (isAttributeToCopy(currentMethodName, propertiesToCopy)) {
					prepareInvokeMethod(from, to, currentMethodInput);
				}
			} catch (Exception e) {
				LOG.error("non sono riuscito a copiare - metodo get del from " + from.getClass().getSimpleName() + "."
						+ currentMethodName + ", returnType " + returnType + ",  metodo set di destinazione "
						+ to.getClass().getSimpleName() + "."
						+ currentMethodName.replaceFirst("get", "set").replaceFirst("is", "set"));
				LOG.error("Eccezione : " + e);
			}
		}
		return to;
	}

	/**
	 * esegue il metodo copy properties dall'oggetto di input e dall'oggetto
	 * primaryKey allegato, adottando sempre il filtro delle proprietà da
	 * copiare
	 * 
	 * @param from
	 * @param fromPrimaryKey
	 * @param to
	 * @param propertiesToCopy
	 * @return
	 * @throws TechnicalException
	 */
	public static <T> T copyPropertiesFromExternalId(T from, T fromPrimaryKey, T to, String[] propertiesToCopy) {
		copyProperties(from, to, propertiesToCopy);
		copyProperties(fromPrimaryKey, to, propertiesToCopy);
		return to;
	}

	/**
	 * effettua due controlli: - il metodo di input deve iniziare con "get" o
	 * con "is" - l'attributo di cui si chiama la "set" deve essere contenuto
	 * nella lista passata in input
	 * 
	 * @param methodName
	 * @param listAcceptedAttributes
	 * @return boolean
	 */
	private static boolean isAttributeToCopy(String methodName, String[] listAcceptedAttributes) {
		for (String currentAttribute : listAcceptedAttributes) {
			if (methodName.startsWith("get") && currentAttribute.equalsIgnoreCase(methodName.replaceFirst("get", ""))
					|| methodName.startsWith("is")
							&& currentAttribute.equalsIgnoreCase(methodName.replaceFirst("is", "")))
				return true;
		}
		return false;
	}

	/**
	 * Questo metodo invoca il relativo metodo di "Set" di destinazione.
	 * Converte anche i "Date" in "XMLGregorianCalendar" e viceversa.
	 * 
	 * @param input
	 * @param output
	 * @param currentMethodInput
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws DatatypeConfigurationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 */
	private static <T> void prepareInvokeMethod(T from, T to, Method currentMethodInput) {
		try {
			String currentMethodName = currentMethodInput.getName();
			Object inputValue = currentMethodInput.invoke(from);
			Class<?> classSetValue = currentMethodInput.getReturnType();
			invokeMethod(inputValue, classSetValue, currentMethodName, to);
		} catch (Exception e) {
			LOG.error(" error prepareInvokeMethod " + e.getMessage(), e);
		}
	}

	private static <T> void invokeMethod(Object inputValue, Class<?> classSetValue, String currentMethodName, T output)
			throws DatatypeConfigurationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object value = inputValue;
		Class<?> classSet = getCorrectClassSet(classSetValue, output, currentMethodName);
		output.getClass().getMethod(replaceMethodName(currentMethodName), classSet).invoke(output, value);
	}

	private static <T> Class<?> getCorrectClassSet(Class<?> classSetValue, T output, String currentMethodName) {
		Class<?> classSet;
		classSet = classSetValue;
		for (Field current : output.getClass().getDeclaredFields()) {
			if (current.isAnnotationPresent(XmlElement.class)
					&& current.getName().equalsIgnoreCase(replaceMethodName(currentMethodName).replace("set", ""))) {
				XmlElement xmlEl = current.getAnnotation(XmlElement.class);
				if (xmlEl.type() != null && xmlEl.type().equals(Long.class))
					classSet = Long.class;
			}
		}
		return classSet;
	}

	private static String replaceMethodName(String currentMethodName) {
		if (currentMethodName.startsWith("is") || currentMethodName.startsWith("get"))
			return currentMethodName.replaceFirst("is|get", "set");
		return currentMethodName;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static <T> T copyPropertiesNotNull(T from, T to, String[] propertiesToCopy) {
		String currentMethodName;
		String returnType;

		for (Method currentMethodInput : from.getClass().getMethods()) {
			currentMethodName = currentMethodInput.getName();
			returnType = currentMethodInput.getReturnType().getSimpleName();
			try {
				if (isAttributeToCopy(currentMethodName, propertiesToCopy) && currentMethodInput.invoke(from) != null) {
					prepareInvokeMethod(from, to, currentMethodInput);
				}
			} catch (Exception e) {
				LOG.error("non sono riuscito a copiare - metodo get del from " + from.getClass().getSimpleName() + "."
						+ currentMethodName + ", returnType " + returnType + ",  metodo set di destinazione "
						+ to.getClass().getSimpleName() + "."
						+ currentMethodName.replaceFirst("get", "set").replaceFirst("is", "set"));
				LOG.error("Eccezione : " + e);
			}
		}
		return to;
	}
}