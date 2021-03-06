package com.nealxyc.dryunit.paramter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.nealxyc.dryunit.parameter.WithParams;

public class ParameterResolverTest {

    @Test
    public void testPermutateParams(){
	Integer[] v1 = {1,2,3};
	Integer[] v2 = {1,2,3,4};
	
	Collection<Object[]> params = WithParams.Helper.permuateToParameters(new List[]{Arrays.asList(v1),Arrays.asList( v2)});
	Assert.assertEquals(12, params.size());
	
	Integer[] v3 = {1,2,3,4};
	
	params = WithParams.Helper.permuateToParameters(new List[]{Arrays.asList(v1),Arrays.asList( v2), Arrays.asList(v3)});
	Assert.assertEquals(48, params.size());
	
//	params = WithParams.Helper.permuateToParameters(new List[]{Arrays.asList(new Integer[]{1, 2}),Arrays.asList(v1)});
//	
//	Assert.assertEquals(Sets.newHashSet(new Integer[][]{{1,1}, {1, 2}, {1, 3}, {2, 1}, {2, 2}, {2, 3}}), Sets.newHashSet(params)); 
    }
}
