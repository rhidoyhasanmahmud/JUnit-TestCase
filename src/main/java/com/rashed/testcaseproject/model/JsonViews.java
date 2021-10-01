package com.rashed.testcaseproject.model;

public class JsonViews {

	/** Smallest view, with name / title and max two other fields */
	public static class Small { }

	/** Brief view of the object with usually 5-6 fields */
	public static class Brief extends Small { }

	/** Full detail view with all fields, except the ignored one */
	public static class Detail extends Brief { }

}
