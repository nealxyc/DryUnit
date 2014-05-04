package com.nealxyc.dryunit;


import org.junit.Assert;
import org.junit.Test;

public class ArrayTypeTest {

	@Test
	public void testArrayTypes(){
		int[]  ints = {1,2,3};
		Assert.assertTrue(ints instanceof Object);
		Assert.assertTrue(ints.getClass().isArray());
	}
}
