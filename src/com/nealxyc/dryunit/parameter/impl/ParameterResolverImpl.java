package com.nealxyc.dryunit.parameter.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;

import com.nealxyc.dryunit.parameter.ParameterResolver;

public class ParameterResolverImpl implements ParameterResolver {

	private HashMap<String, Collection<Object>> paramsMap  = new HashMap<String, Collection<Object>>();
	private HashMap<String, ParamDefinition> paramsDefMap  = new HashMap<String, ParamDefinition>();
	
	@Override
	public Object[] resolve(ParamRef ref) {
		// TODO Auto-generated method stub
		return null;
	}

	public void put(String id, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scan(Class<?> cls) {
	    Field[] fields = cls.getFields();
	    for(Field field: fields){
//		ParamDefinition[] defs =// ParamDefinition.Helper.
	    }
	}

}
