package com.nealxyc.dryunit.parameter;

public class ParamResolveException extends Exception{

	public ParamResolveException(String msg, Exception e){
		super(msg, e);
	}
	
	public ParamResolveException(String msg){
		super(msg);
	}
	
}
