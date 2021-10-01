package com.rashed.testcaseproject.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * Convert any collection to JSON string and vice-versa.
 * 
 * @author Imtiaz Rahi
 * @since 2019-01-16
 */
@Converter
public class CollectionToStringConverter implements AttributeConverter<Collection<String>, String> {
	public static String DELIMITER = ",";

	@Override
	public String convertToDatabaseColumn(Collection<String> meta) {
		if (meta == null || meta.isEmpty()) return null;
		return String.join(DELIMITER, meta);
	}

	@Override
	public Collection<String> convertToEntityAttribute(String data) {
		if (data == null || data.isEmpty()) return null;
		return Arrays.asList(data.split(DELIMITER));
	}

}
