package com.nealxyc.dryunit.runner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DryRunner.class)
public class DryRunnerTest {
    
    @Test
    public void normalTest(){
	Assert.assertEquals(1,1);
    }
    
    @Test
    public void hasArgument(int v1, int v2){
	//Assert.assertEquals(v1,v2);
    }
}
