package com.nealxyc.dryunit.paramter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.WithParams;
import com.nealxyc.dryunit.parameter.impl.ParamRefImpl;
import com.nealxyc.dryunit.runner.DryRunner;

@RunWith(DryRunner.class)
public class ParamRefTest {

    public static class MyTestClass{
	
	@Test
	@WithParams({"v1", "v2", "v3"})
	public void testWithTwoArgs(int v1, int v2){
	    //Dont run this.
	    Assert.fail();
	}

    }
    
    @Test(expected=ParamResolveException.class)
    public void testDiffNumOfArgs() throws SecurityException, ParamResolveException, NoSuchMethodException{
	ParamRefImpl.Helper.getFromMethod(MyTestClass.class.getMethod("testWithTwoArgs", new Class<?>[]{Integer.TYPE, Integer.TYPE}));
    }
    
    
}
