package com.nealxyc.dryunit.parameter;

public @interface ParamDef {
	String name();
	Class<?> type() default Object.class;
}
