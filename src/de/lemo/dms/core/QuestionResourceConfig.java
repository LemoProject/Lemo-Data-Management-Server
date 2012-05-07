package de.lemo.dms.core;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.core.spi.scanning.Scanner;
import com.sun.jersey.spi.scanning.AnnotationScannerListener;

import de.lemo.dms.processing.QuestionID;

/**
 * Extended {@link PackagesResourceConfig} which scans for {@link QuestionID}
 * instead of {@link Path} annotations.
 * 
 * @author Leonard Kappe
 * 
 */
public class QuestionResourceConfig extends PackagesResourceConfig {

    public QuestionResourceConfig(String... questionPackages) {
        super(questionPackages);
    }

    @Override
    public void init(Scanner scanner) {
        @SuppressWarnings("unchecked")
        AnnotationScannerListener scannerListener = new AnnotationScannerListener(QuestionID.class);
        scanner.scan(scannerListener);
        getClasses().addAll(scannerListener.getAnnotatedClasses());

        Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();
        if(logger.isInfoEnabled() && !getClasses().isEmpty()) {
            logger.info("Questions resource classes found:");
            for(Class<?> question : getClasses()) {
                logger.info("  " + question);
            }
        }
    }

}
