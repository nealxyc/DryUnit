package com.nealxyc.dryunit.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.WithParams;
import com.nealxyc.dryunit.runner.DryRunner;

@RunWith(DryRunner.class)
public class FibonacciTest {
    
    @Test
    @WithParams({"fibin","fibout"})
    public void test(int input, int output) {
        assertEquals(output, fibonacci(input));
    }
    
    @ParamValues("fibin")
    public static int[] fibin = {0, 1, 2, 3};
    
    @ParamValues("fibout")
    public static int[] fibout = {0, 1, 1, 2};
    
    public int fibonacci(int num){
	if(num == 0){
	    return 0;
	}
	if(num == 1){
	    return 1;
	}
	return fibonacci(num - 2) + fibonacci(num - 1);
    }
}