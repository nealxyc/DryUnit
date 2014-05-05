package com.nealxyc.dryunit.runner;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamTest;
import com.nealxyc.dryunit.parameter.ParamTest.Mode;
import com.nealxyc.dryunit.parameter.ParamValues;

@RunWith(DryRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DryRunnerTest {
    
	private static int counter = 0;
	
    @Test
    public void normalTest(){
	Assert.assertEquals(1,1);
    }
    
    @Test
    public void hasArgument(@Param("v1") int v1, @Param("v2") int v2){
    	Assert.assertEquals(v1,v2);
    }
    
    @Test
    @ParamTest(mode=Mode.PERMUTATION)
    public void independentArguments(@Param(value="v1") int v1, @Param("v2") int v2){
    	//
    	counter ++ ;
    	
    }
    
    @Test
    public void ztestMultiplyCount(){
    	Assert.assertEquals(16, counter);
    }
    
    @ParamValues({"v1"})
    public static int[] ints = {1,2,3, 4};
    
    @ParamValues({"v2"})
    public static int[] v2ints = {1,2,3, 4};
}
