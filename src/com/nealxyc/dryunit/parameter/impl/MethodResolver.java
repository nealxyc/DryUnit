package com.nealxyc.dryunit.parameter.impl;

import java.lang.reflect.Method;
import java.util.Collection;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParameterResolver;
import com.nealxyc.dryunit.parameter.Resolver;

public class MethodResolver implements Resolver<Collection<Object>, ParameterResolver> {

	private ParamRef[] refs;
	private Method method;

	public MethodResolver(Method m, ParamRef... refs) {
		this.method = m;
		this.refs = refs;
	}

	@Override
	public Collection<Object> resolve(ParameterResolver resolver) {
		//TODO
		return null ;
	}

	public Collection<Object> resolve(ParameterResolver obj, ResolverState state)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}