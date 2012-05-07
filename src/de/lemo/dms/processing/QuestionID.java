package de.lemo.dms.processing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.Path;

/**
 * Replaces {@link Path} annotation for questions to modify the resource URL. A
 * Path annotation would the cause explicit URL mapping for question to be
 * ignored.
 * 
 * @author Leonard Kappe
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QuestionID {
    String value();
}
