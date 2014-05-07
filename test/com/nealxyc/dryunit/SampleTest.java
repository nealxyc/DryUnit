package com.nealxyc.dryunit;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nealxyc.dryunit.runner.DryRunner;
import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.WithParams;

@RunWith(DryRunner.class)
public class SampleTest {

	@Test
	@WithParams({"v1", "v2"})
	public void testMethod(int value, int v2){
		Assert.assertEquals(value, v2);
	}
	
	@ParamValues("v1")
	public static int[] values = {1,2,3} ;
	
	@ParamValues("v2")
	public static int valuesSupplier(){
	    return -1;
	}
}
