package com.nealxyc.dryunit.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.WithParams.Mode;
import com.nealxyc.dryunit.parameter.WithParams;

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
		    WithParams params = method.getAnnotation(WithParams.class);
		    List<ParamRefImpl> refs = new ArrayList<ParamRefImpl>();
		    Class<?>[] paramTypes = method.getParameterTypes();
		    String[] ids = params.value() ;
		    if(params.value().length != paramTypes.length){
			throw new ParamResolveException(String.format("Method %s() has %s parameters but @%s contains %s values", method.getName() , paramTypes.length, WithParams.class.getSimpleName(), params.value().length));
		    }
		    if(paramTypes.length > 0){
		    	for(int i = 0 ; i < paramTypes.length; i ++){
		    		ParamRefImpl ref = new ParamRefImpl();
		    		ref.setId(ids[i]);
//    				ref.setMode(param.mode());
    				ref.setType(paramTypes[i]);
    				refs.add(ref);
		    	}
		    }
		    return refs.toArray(new ParamRefImpl[0]) ;
		}
	}

}
