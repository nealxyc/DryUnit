package com.nealxyc.dryunit.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParamTest.Mode;

public class ParamRefImpl implements ParamRef {

	private String id;
	private Class<?> type;
	private Mode mode ;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public void setType(Class<?> type) {
		this.type = type;
	}

	@Override
	public Mode getMode() {
		return mode;
	}

	@Override
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public static class Helper {

		public static ParamRefImpl[] getFromMethod(Method method) throws ParamResolveException{
		    List<ParamRefImpl> refs = new ArrayList<ParamRefImpl>();
		    Annotation[][] paramAnnotations = method.getParameterAnnotations();
		    Class<?>[] paramTypes = method.getParameterTypes();
		    if(paramTypes.length > 0){
		    	for(int i = 0 ; i < paramTypes.length; i ++){
		    		Annotation[] annotations = paramAnnotations[i];
		    		ParamRefImpl ref = new ParamRefImpl();
		    		for(Annotation an: annotations){
		    			if(an instanceof Param){
		    				Param param = (Param)an ;
		    				ref.setId(param.value());
		    				ref.setMode(param.mode());
		    				ref.setType(paramTypes[i]);
		    				refs.add(ref);
		    				break ;
		    			}
		    		}
		    	}
		    }
		    
		    if(refs.size() != paramTypes.length){
		    	throw new ParamResolveException(String.format("All parameters have to be annotated with @%s", Param.class.getSimpleName()));
		    }
		    return refs.toArray(new ParamRefImpl[0]) ;
		}
	}

}
