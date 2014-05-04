package com.nealxyc.dryunit;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nealxyc.dryunit.runner.DryRunner;
import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamValues;

@RunWith(DryRunner.class)
public class SampleTest {

	@Test
	public void testMethod(@Param("v1") int value, @Param("v2") int v2){
		Assert.assertEquals(value, v2);
	}
	
	public static @ParamValues({"v1", "v2"}) int[] values = {1,2,3}; 
}
