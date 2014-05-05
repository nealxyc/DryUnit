package com.nealxyc.dryunit.parameter;

import com.nealxyc.dryunit.parameter.impl.ParamRef;

public interface ParameterResolver extends Resolver<Object[], ParamRef>{
	public Object[] resolve(ParamRef ref);
//	public void put(String id, Object value);
	public void scan(Class<?> cls);
}
