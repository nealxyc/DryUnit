package com.nealxyc.dryunit.runner;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParameterResolver;
import com.nealxyc.dryunit.parameter.WithParams;
import com.nealxyc.dryunit.parameter.impl.ParamRefImpl;
import com.nealxyc.dryunit.parameter.impl.ParameterResolverImpl;

public class DryRunner extends BlockJUnit4ClassRunner {

    private ParameterResolver resolver = null;

    public DryRunner(Class<?> klass) throws InitializationError {
	super(klass);
	// try {
	// // resolver =
	// } catch (ParamResolveException e) {
	// throw new InitializationError(e);
	// }
    }

    protected ParameterResolver createParameterResolver(Class<?> cls)
	    throws ParamResolveException {
	ParameterResolverImpl resolver = new ParameterResolverImpl();
	resolver.scan(cls);
	return resolver;
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
	try {
	    return computeDryTestMethods(getTestClass().getAnnotatedMethods(
		    Test.class));
	} catch (ParamResolveException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;

    }

    protected List<FrameworkMethod> computeDryTestMethods(
	    List<FrameworkMethod> fwMethods) throws ParamResolveException {
	if (this.resolver == null) {
	    this.resolver = createParameterResolver(this.getTestClass()
		    .getJavaClass());
	}
	List<FrameworkMethod> list = Lists.newArrayList();
	for (FrameworkMethod fwMethod : fwMethods) {
	    list.addAll(computeDryTestMethods(fwMethod));
	}
	return list;
    }

    protected List<FrameworkMethod> computeDryTestMethods(
	    FrameworkMethod fwMethod) throws ParamResolveException {

	List<FrameworkMethod> list = Lists.newArrayList();
	if (isDryTestMethod(fwMethod)) {
	    ParamRef[] refs = ParamRefImpl.Helper.getFromMethod(fwMethod
		    .getMethod());
	    Collection<Object[]> params = resolver.resolve(
		    fwMethod.getAnnotation(WithParams.class), refs);
	    Iterator<Object[]> itr = params.iterator();
	    for (int i = 0; i < params.size(); i++) {
		list.add(new DryFrameworkMethod(fwMethod.getMethod(), i, itr
			.next()));
	    }

	} else {
	    list.add(fwMethod);
	}

	return list;
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
	return method.getMethod().getParameterTypes().length > 0
		&& method.getAnnotation(WithParams.class) != null;
    }
}
