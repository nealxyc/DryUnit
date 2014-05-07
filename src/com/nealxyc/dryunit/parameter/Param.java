package com.nealxyc.dryunit.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nealxyc.dryunit.parameter.WithParams.Mode;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
	String value();
	Class<?> type() default Object.class;
	Mode mode() default Mode.GROUP;

}
