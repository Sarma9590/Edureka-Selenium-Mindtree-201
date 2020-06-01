package com.mercury.org.framework.constants;

public enum LocatorType {

	ID("id"), CSSSELECTOR("cssselector"), XPATH("xpath"), CLASSNAME("classname"), NAME("name"), TAGNAME(
			"tagname"), LINKTEXT("linktext"), PARTIALLINKTEXT("partiallinktext");

	private String type;

	private LocatorType(String type) {
		this.type = type;
	}

	public String get() {
		return type;
	}

}
