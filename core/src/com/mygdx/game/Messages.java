package com.mygdx.game;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum Messages {

	NEW_GAME("new.game"),
	OPTIONS("options"),
	ABOUT("about");

	private final String key;
	private static final String BUNDLE_NAME = "MessagesBundle";
	private static final ResourceBundle RESOURCE_BUNDLE;
	
	static {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME) == null ?
				ResourceBundle.getBundle(BUNDLE_NAME, Locale.US) :
				ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	private Messages(final String key) {
		this.key = key;
	}
	
	
	public String getValue() {
		return RESOURCE_BUNDLE.getString(key);
	}

	public String getValue(Object... params) {
		return MessageFormat.format(RESOURCE_BUNDLE.getString(key), params);
	}
}
