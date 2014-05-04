package com.nealxyc.dryunit.runner;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.nealxyc.dryunit.parameter.ParameterResolver;
import com.nealxyc.dryunit.parameter.impl.ParameterResolverImpl;

public class DryRunner extends BlockJUnit4ClassRunner{

    private ParameterResolver resolver = new ParameterResolverImpl();
    public DryRunner(Class<?> klass) throws InitializationError {
	super(klass);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
	return wrapFramworkMethodList(getTestClass().getAnnotatedMethods(
		Test.class));

    }

    /**
     * Will skip the no-argument validation on Annotation @Test .<br>
     * Adds to {@code errors} if any method in this class is annotated with
     * {@code annotation}, but:
     * <ul>
     * <li>is not public, or
     * <li>takes parameters, or (this step will be skipped for @Test)
     * <li>returns something other than void, or
     * <li>is static (given {@code isStatic is false}), or
     * <li>is not static (given {@code isStatic is true}).
     */
    @Override
    protected void validatePublicVoidNoArgMethods(
	    Class<? extends Annotation> annotation, boolean isStatic,
	    List<Throwable> errors) {
	List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(
		annotation);
	if (Test.class.equals(annotation)) {
	    methods = wrapFramworkMethodList(methods);
	}
	for (FrameworkMethod eachTestMethod : methods) {
	    eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
	}
    }

    protected List<FrameworkMethod> wrapFramworkMethodList(
	    List<FrameworkMethod> list) {
	return Lists.newArrayList(Collections2.transform(list,
		new Function<FrameworkMethod, FrameworkMethod>() {

		    @Override
		    public FrameworkMethod apply(FrameworkMethod input) {
			if (isDryTestMethod(input)) {
			    return wrapFrameworkMethod(input);
			} else {
			    return input;
			}

		    }
		}));
    }

    protected FrameworkMethod wrapFrameworkMethod(FrameworkMethod method) {
	return new DryFrameworkMethod(method);
    }

    protected boolean isDryTestMethod(FrameworkMethod method) {
	return method.getMethod().getParameterTypes().length > 0;
    }
}
