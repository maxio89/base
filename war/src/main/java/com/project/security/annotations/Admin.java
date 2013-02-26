package com.project.security.annotations;

import org.jboss.seam.security.annotations.SecurityBindingType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: Rafal Gielczowski
 * Date: 2/26/13 Time: 1:38 PM
 * <p/>
 * Security binding for @Admin role.
 */
@SecurityBindingType
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface Admin {

}
