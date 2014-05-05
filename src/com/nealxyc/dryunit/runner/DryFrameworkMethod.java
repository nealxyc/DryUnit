package com.nealxyc.dryunit.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.runners.model.FrameworkMethod;

public class DryFrameworkMethod extends FrameworkMethod {

	public static final int UNKNOWN =  -1;
	private final int index;
	private final Object[] params;

	public DryFrameworkMethod(Method method) {
		this(method, UNKNOWN, null);
	}

	public DryFrameworkMethod(Method method, int index, Object[] params) {
		super(method);
		this.params = params;
		this.index = index ;
	}

	public DryFrameworkMethod(FrameworkMethod method) {
		this(method.getMethod(), UNKNOWN, null);
	}

	/**
	 * Overwrites FrameworkMethod.validatePublicVoidNoArg() and skips the
	 * validation on no-arguments<br>
	 * Adds to {@code errors} if this method:
	 * <ul>
	 * <li>is not public, or
	 * <li>returns something other than void, or
	 * <li>is static (given {@code isStatic is false}), or
	 * <li>is not static (given {@code isStatic is true}).
	 */
	public void validatePublicVoidNoArg(boolean isStatic, List<Throwable> errors) {
		validatePublicVoid(isStatic, errors);
		// if (super.getMethod().getParameterTypes().length != 0) {
		// errors.add(new Exception("Method " + fMethod.getName() +
		// " should have no parameters"));
		// }
	}

	protected Object[] getParams() {
		return params;
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

	/**
     * Returns the method's name
     */
    @Override
    public String getName() {
        return super.getName() + indexCurrentMethod();
    }
    
    protected String indexCurrentMethod(){
    	if(this.index != UNKNOWN){
    		return "[" + this.index + "]";
    	}else{
    		return "";
    	}
    }
}
