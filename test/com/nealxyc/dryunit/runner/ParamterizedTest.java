package com.nealxyc.dryunit.runner;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;

@RunWith(Parameterized.class)
public class ParamterizedTest {

    private int v1;
    private int v2;
    
    public ParamterizedTest(int v1, int v2){
	this.v1 = v1;
	this.v2 = v2 ;
	
    }
    
    @Parameters
    public static Collection<Object[]> data(){
	return (List)Lists.newArrayList(new Object[]{1,1}, new Object[]{2,2});
    }
    
    @Test
    public void doTest(){
	Assert.assertEquals(v1, v2);
    }
}
