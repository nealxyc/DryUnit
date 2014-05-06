package com.nealxyc.dryunit.parameter.impl;

import java.lang.reflect.Method;
import java.util.Collection;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParamResolveException;
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
	public Collection<Object> resolve(ParameterResolver resolver) throws ParamResolveException {
		return resolve(resolver, new ResolverState()) ;
	}

	public Collection<Object> resolve(ParameterResolver resolver, ResolverState state)
			throws ParamResolveException {
		for(int i = 0 ; i < refs.length; i ++){
			ParamRef ref = refs[i];
			Collection<Object> values = resolver.resolve(ref);
		}
		return null;
	}

}