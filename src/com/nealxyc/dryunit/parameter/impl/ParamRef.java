package com.nealxyc.dryunit.parameter.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParamRef {

    private String id ;
    private Class<?> type ;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Class<?> getType() {
        return type;
    }
    public void setType(Class<?> type) {
        this.type = type;
    }
    
    public static class Helper{
	
	public static ParamRef[] getFromMethod(Method method){
	    List<ParamRef> refs = new ArrayList<ParamRef>();
	    if(method.get)
	}
    }
    
}
