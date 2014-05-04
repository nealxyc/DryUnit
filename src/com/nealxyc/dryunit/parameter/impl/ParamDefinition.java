package com.nealxyc.dryunit.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.impl.ParamDefinitionValue.ParamResolveType;

public class ParamDefinition {

    private ParamDefinition() {
    }

    private String id;// unique id
    private ParamDefinitionValue value;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public ParamDefinitionValue getValue() {
	return value;
    }

    public void setValue(ParamDefinitionValue value) {
	this.value = value;
    }

    public static class Helper {

	public static ParamDefinition[] getFromField(Field field)
		throws ParamResolveException {
	    if (!Modifier.isStatic(field.getModifiers())) {
		throw new ParamResolveException(
			"@ParamValues has to be annotated on static field/method.");
	    }
	    Annotation[] annotations = field.getAnnotations();
	    List<ParamValues> paramValuesAnno = new ArrayList<ParamValues>();
	    List<ParamDefinition> defList = new ArrayList<ParamDefinition>();

	    for (Annotation an : annotations) {
		if (an instanceof ParamValues) {
		    paramValuesAnno.add((ParamValues) an);
		}
	    }

	    if (paramValuesAnno.size() > 0) {
		ParamDefinitionValue value = new ParamDefinitionValue()
			.setParamResolveType(ParamResolveType.FIELD);

		Object fieldValue = null;
		try {
		    fieldValue = field.get(null);
		} catch (IllegalArgumentException e) {
		    throw new ParamResolveException(String.format(
			    "Cannot retreive value from field: %s.%s", field
				    .getDeclaringClass().getSimpleName(), field
				    .getName()), e);
		} catch (IllegalAccessException e) {
		    throw new ParamResolveException(String.format(
			    "Cannot retreive value from field: %s.%s", field
				    .getDeclaringClass().getSimpleName(), field
				    .getName()), e);
		} catch (NullPointerException e) {
		    // requires an instance to get the value
		}
		List<Object> resolvedValue = new ArrayList<Object>();
		Class<?> elemType = Object.class; // default element type is
						  // object

		if (fieldValue != null) {
		    if (fieldValue.getClass().isArray()) {
			// Deal with array
			int length = Array.getLength(fieldValue);
			for (int i = 0; i < length; i++) {
			    resolvedValue.add(Array.get(fieldValue, i));
			}
			elemType = fieldValue.getClass().getComponentType();
		    } else if (Collection.class.isAssignableFrom(fieldValue
			    .getClass())) {

			Collection c = (Collection) fieldValue;
			resolvedValue.addAll(c);
			if (c.size() > 0) {
			    elemType = c.iterator().next().getClass();
			}

		    } else if (Iterable.class.isAssignableFrom(fieldValue
			    .getClass())) {
			Iterable itr = (Iterable) fieldValue;
			boolean gotType = false;
			for (Object elem : itr) {
			    resolvedValue.add(elem);
			    if (!gotType) {
				elemType = elem.getClass();
				gotType = true;
			    }
			}
		    } else {
			throw new ParamResolveException(
				String.format(
					"Target field is not Collection, Array, or Iterable type: %s.%s",
					field.getDeclaringClass()
						.getSimpleName(), field
						.getName()));
		    }
		} else {
		    // null
		}

		value.setResolvedValues(resolvedValue);
		value.setResolved(true);
		value.setElementType(elemType);
		List<ParamDefinition> defs = new ArrayList<ParamDefinition>();
		for (ParamValues pv : paramValuesAnno) {
		    String[] ids = pv.value();
		    for (String id : ids) {
			ParamDefinition def = new ParamDefinition();
			def.setId(id);
			def.setValue(value);
			defList.add(def);
		    }
		}

	    }

	    return defList.toArray(new ParamDefinition[0]);
	}

	// public static ParamDefinition[] getFromMethod(Object obj, Method
	// method){
	//
	// }
    }
}
