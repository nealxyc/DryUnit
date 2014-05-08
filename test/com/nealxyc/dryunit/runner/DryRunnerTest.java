package com.nealxyc.dryunit.runner;

import java.util.Arrays;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.WithParams.Mode;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.WithParams;

@RunWith(DryRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DryRunnerTest {
    
	private static int counter = 0;
	
    @Test
    public void normalTest(){
    	Assert.assertEquals(1,1);
    }
    
    @Test
    @WithParams({"v1", "v2"})
    public void hasArgument( int v1,int v2){
    	Assert.assertEquals(v1,v2);
    }
    
    @Test
    @WithParams(value={"v1", "v4"}, mode=Mode.PERMUTATION)
    public void independentArguments(int v1,int v2){
    	counter += v1 + v2 ;
    }
    
    @Test
    @Ignore
    @WithParams({"v1", "v3"})
    public void testMissingAnnotation(int v1,int v2){
    	Assert.assertTrue(Arrays.asList(ints).contains(v1));
    }
    
    @AfterClass
    public static void tearDownOnce(){
	Assert.assertEquals(120, counter);
    }
    
    @ParamValues("v1")
    public static int[] ints = {1,2,3, 4};
    
    @ParamValues("v2")
    public static int[] v2ints = {1,2,3, 4};
    
    @ParamValues("v3")
    public static int[] v3 = {1,2,3,4};

    @ParamValues("v4")
    public static int[] v4 = {5, 5,5,5};
    
}
