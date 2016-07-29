package org.to2mbn.lolixl.ui.theme;

import java.util.Map;

public interface Theme {
	String PROPERTY_FILE_NAME = "meta.json";
	String PROPERTY_KEY_ID = "id";
	String PROPERTY_KEY_AUTHORS = "authors";
	String PROPERTY_KEY_ICON_LOCATION = "icon";
	String INTERNAL_PROPERTY_KEY_PACKAGE_URL = "package_url";

	String getId();

	Map<String, Object> getMeta();

	String[] getStyleSheets();

	ClassLoader getResourceLoader();
}