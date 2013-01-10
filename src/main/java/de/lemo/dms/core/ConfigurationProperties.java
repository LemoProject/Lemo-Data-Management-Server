package de.lemo.dms.core;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ConfigurationProperties {

    private static final String BUNDLE_NAME = "de.lemo.dms.core.config"; //$NON-NLS-1$
    private static final String DEFAULT_BUNDLE_NAME = "de.lemo.dms.core.default_config"; //$NON-NLS-1$

    private static ResourceBundle resourceBundle;
    static {
        try {
            resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (java.util.MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle(DEFAULT_BUNDLE_NAME);
            Logger.getLogger(ConfigurationProperties.class).warn(
                "Using default configuration " + DEFAULT_BUNDLE_NAME
                        + "\nTo override default configuration, create file /main/resources/"
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
