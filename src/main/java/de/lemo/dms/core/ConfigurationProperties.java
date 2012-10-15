package de.lemo.dms.core;

import java.util.ResourceBundle;

public class ConfigurationProperties {

    private static final String BUNDLE_NAME = "de.lemo.dms.core.config"; //$NON-NLS-1$
    private static final String DEFAULT_BUNDLE_NAME = "de.lemo.dms.core.default_config"; //$NON-NLS-1$

    private static ResourceBundle resourceBundle;
    static {
        try {
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (java.util.MissingResourceException e) {
            System.out.println("Using default configuration " + DEFAULT_BUNDLE_NAME);
            resourceBundle = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME);
            System.out.println("To override default configuration, create file /main/resources/"
                    + BUNDLE_NAME.replace('.', '/') + ".properties");
        }
    }

    private ConfigurationProperties() {
        // no instance needed
    }

    public static String getPropertyValue(String key) {
        return resourceBundle.getString(key);
    }
}
