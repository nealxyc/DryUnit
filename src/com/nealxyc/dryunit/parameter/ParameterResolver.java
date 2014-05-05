package com.nealxyc.dryunit.parameter;

import java.util.Collection;


public interface ParameterResolver extends Resolver<Collection<Object>, ParamRef>{
	public Collection<Object> resolve(ParamRef ref) throws ParamResolveException;
	Collection<Object[]> resolve(ParamTest testAnnotation, ParamRef... refs) throws ParamResolveException;
//	public void put(String id, Object value);
//	public void scan(Class<?> cls) throws ParamResolveException;
}
