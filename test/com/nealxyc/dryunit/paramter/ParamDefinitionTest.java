package com.nealxyc.dryunit.paramter;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.impl.ParamDefinition;

public class ParamDefinitionTest {

	public static class MyClass{
	    
	    	public static final String tag = "myVar";
		public static @ParamValues("ints") int[] ints = {1,2,3};
		public static @ParamValues("doubles") double[] doubles = {1,2,3};
		public static @ParamValues("strings") String[] strings = {"a", "b", "c"};
		public static @ParamValues("objects") Object[] objects = {new Object(), "b", "c"};
		public @ParamValues("instanceInts") int[] instanceInts = {1,2,3};
		public static @ParamValues({"ints2", "ints3"}) int[] ints2 = {1,2,3};
		
		private static @ParamValues({"ints2", "ints3"}) int[] ints3 = {1,2,3};
		public static @ParamValues("myInt") int myInt = 123;
		
		public static int[] ints4 = {1,2,3};
		
		public static @ParamValues(tag) int[] tags = {1,2,3};
		
	}
	
	@Test
	public void testReadParamDefFromField() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getField("ints");
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
		
		Assert.assertEquals(1, defs.length);
		Assert.assertEquals("ints", defs[0].getId());
		Assert.assertEquals(Lists.newArrayList(1, 2, 3), defs[0].getValue().getResolvedValues());
		Assert.assertTrue(defs[0].getValue().isResolved());
		
		Field doubles = MyClass.class.getField("doubles");
		defs = ParamDefinition.Helper.getFromField(doubles);
		
		Assert.assertEquals(1, defs.length);
		Assert.assertEquals("doubles", defs[0].getId());
		Assert.assertEquals(Lists.newArrayList(1.0, 2.0, 3.0), defs[0].getValue().getResolvedValues());
		Assert.assertTrue(defs[0].getValue().isResolved());
		
		Field strings = MyClass.class.getField("strings");
		defs = ParamDefinition.Helper.getFromField(strings);
		
		Assert.assertEquals(1, defs.length);
		Assert.assertEquals("strings", defs[0].getId());
		Assert.assertEquals(Lists.newArrayList(MyClass.strings), defs[0].getValue().getResolvedValues());
		Assert.assertTrue(defs[0].getValue().isResolved());
		
		Field objects = MyClass.class.getField("objects");
		defs = ParamDefinition.Helper.getFromField(objects);
		
		Assert.assertEquals(1, defs.length);
		Assert.assertEquals("objects", defs[0].getId());
		Assert.assertEquals(Lists.newArrayList(MyClass.objects), defs[0].getValue().getResolvedValues());
		Assert.assertTrue(defs[0].getValue().isResolved());
	}
	
	@Test(expected=ParamResolveException.class)
	public void testReadParamDefFromInstanceField() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getField("instanceInts");
		MyClass mc = new MyClass();
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
	}
	
	@Test
	public void testReadMutipleParamDefFromField() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getField("ints2");
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
		Assert.assertEquals(2, defs.length);
		Assert.assertEquals("ints2", defs[0].getId());
		Assert.assertEquals("ints3", defs[1].getId());
		Assert.assertEquals(Lists.newArrayList(1, 2, 3), defs[0].getValue().getResolvedValues());
		Assert.assertTrue(defs[0].getValue().isResolved());
		Assert.assertEquals(Lists.newArrayList(1, 2, 3), defs[1].getValue().getResolvedValues());
		Assert.assertTrue(defs[1].getValue().isResolved());
	}
	
	@Test(expected=ParamResolveException.class)
	public void testReadParamDefFromPrivateField() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getDeclaredField("ints3");
		//Can not access private field
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
	}
	
	@Test(expected=ParamResolveException.class)
	public void testReadParamDefFromNotCollectionField() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getDeclaredField("myInt");
		//Field is not collection
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
	}
	
	@Test
	public void testReadParamDefFromFieldNoAnnotation() throws SecurityException, NoSuchFieldException, ParamResolveException{
		Field ints = MyClass.class.getDeclaredField("ints4");
		//Field is not collection
		ParamDefinition[] defs = ParamDefinition.Helper.getFromField(ints);
		Assert.assertEquals(0, defs.length);
	}
}
