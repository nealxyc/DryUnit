package com.nealxyc.dryunit.parameter.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.Resolver;

public class ParamDefinitionValue {
    
	public static enum ParamResolveType {
		FIELD,
		METHOD
	}
	
	protected ParamDefinitionValue(){}
	
	private Class<?> elementType ;// param type
	/** The type of object that has ParamValues annotation on, could be a method or a field. */
	private ParamResolveType paramResolveType ;
	private Collection<Object> resolvedValues ;
	private Resolver paramResolver ;
	private boolean resolved ;

	public Class<?> getElementType() {
		return elementType;
	}
	public ParamDefinitionValue setElementType(Class<?> type) {
		this.elementType = type;
		return this ;
	}
	public ParamResolveType getParamResolveType() {
		return paramResolveType;
	}
	public ParamDefinitionValue setParamResolveType(ParamResolveType valueType) {
		this.paramResolveType = valueType;
		return this ;
	}
	public Collection<Object> getResolvedValues() {
		return resolvedValues;
	}
	public ParamDefinitionValue setResolvedValues(Collection<Object> resolvedValues) {
		this.resolvedValues = resolvedValues;
		return this ;
	}
	public boolean isResolved() {
		return resolved;
	}
	public ParamDefinitionValue setResolved(boolean resolved) {
		this.resolved = resolved;
		return this;
	}
	
	public Resolver getParamResolver() {
	    return paramResolver;
	}
	
	public void setParamResolver(Resolver paramResolver) {
	    this.paramResolver = paramResolver;
	}
	
}
