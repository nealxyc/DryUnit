package com.nealxyc.dryunit.runner;

import java.util.Arrays;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamTest;
import com.nealxyc.dryunit.parameter.ParamTest.Mode;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.Params;

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
    @Ignore
    public void testMissingAnnotation(@Param("v1") int v1,@Param("v3") int v2){
    	Assert.assertTrue(Arrays.asList(ints).contains(v1));
    }
    
    @Test
    public void ztestMultiplyCount(){
//    	Assert.assertEquals(16, counter);
    }
    
    @ParamValues({"v1"})
    public static int[] ints = {1,2,3, 4};
    
    @ParamValues({"v2"})
    public static int[] v2ints = {1,2,3, 4};
    
    @ParamValues({"v3"})
    public static int[] v3 = {1,2,3,4};
}
