package de.lemo.dms.core;

import java.util.ResourceBundle;

public class ApplicationProperties {

    private static final String BUNDLE_NAME = "de.lemo.dms.core.appinfo"; //$NON-NLS-1$
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    private ApplicationProperties() {
        // no instance needed
    }

    public static String getPropertyValue(String key) {
        return resourceBundle.getString(key);
    }
}
