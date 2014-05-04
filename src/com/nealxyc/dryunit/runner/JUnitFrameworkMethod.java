package com.nealxyc.dryunit.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.runners.model.FrameworkMethod;

public class JUnitFrameworkMethod extends FrameworkMethod{

    private final Object[] params ;
    
    public JUnitFrameworkMethod(Method method) {
	super(method);
	if(method.getParameterTypes().length > 0){
	    params = new Object[]{1,1};//hack!
	}else{
	    params = new Object[]{};
	}
    }
    
    public JUnitFrameworkMethod(FrameworkMethod method) {
	this(method.getMethod());
    }
    
    /**
     * Overwrites FrameworkMethod.validatePublicVoidNoArg and skips the validation on no-arguments
     * Adds to {@code errors} if this method:
     * <ul>
     * <li>is not public, or
     * <li>returns something other than void, or
     * <li>is static (given {@code isStatic is false}), or
     * <li>is not static (given {@code isStatic is true}).
     */
    public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors) {
        validatePublicVoid(isStatic, errors);
//        if (super.getMethod().getParameterTypes().length != 0) {
//            errors.add(new Exception("Method " + fMethod.getName() + " should have no parameters"));
//        }
    }
    
    protected Object[] getParams(){
	return params ;
    }
    
    /**
     * Returns the result of invoking this method on {@code target} with
     * parameters {@code params}. {@link InvocationTargetException}s thrown are
     * unwrapped, and their causes rethrown.
     */
    public Object invokeExplosively(final Object target, final Object... params)
            throws Throwable {
	
        return new ReflectiveCallable() {
            @Override
            protected Object runReflectiveCall() throws Throwable {
                return getMethod().invoke(target, getParams());
            }
        }.run();
    }

}
