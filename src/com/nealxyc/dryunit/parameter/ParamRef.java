package com.nealxyc.dryunit.parameter;

import com.nealxyc.dryunit.parameter.WithParams.Mode;


public interface ParamRef {

	public String getId();
	public void setId(String id);

	public Class<?> getType();
	public void setType(Class<?> type);

	public Mode getMode();
	public void setMode(Mode mode);

}