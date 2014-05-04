package com.nealxyc.dryunit.parameter;

public interface ParameterResolver {
	public Object[] get(String id);
	public void put(String id, Object value);
}
