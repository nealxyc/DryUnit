package com.nealxyc.dryunit.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ParamTest {
	String value() default "" ;
	Mode mode() default Mode.GROUP;
	
	public static enum Mode {
		GROUP, PERMUTATION
	}
}
