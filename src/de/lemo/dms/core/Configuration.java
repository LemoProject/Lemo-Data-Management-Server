package de.lemo.dms.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configuration {
    private static final String BUNDLE_NAME = "de.lemo.dms.core.config"; //$NON-NLS-1$
    private static final String DEFAULT_BUNDLE_NAME = "de.lemo.dms.core.default_config"; //$NON-NLS-1$

    private static ResourceBundle RESOURCE_BUNDLE;
    static {
        try {
            RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (java.util.MissingResourceException e) {
            System.out.println("'" + BUNDLE_NAME + "' not found, using default configuration '" + DEFAULT_BUNDLE_NAME
                    + "'");
            RESOURCE_BUNDLE = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME);
        }
    }

    private Configuration() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
