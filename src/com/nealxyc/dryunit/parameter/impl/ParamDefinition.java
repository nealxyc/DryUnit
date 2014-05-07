package com.nealxyc.dryunit.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nealxyc.dryunit.parameter.Param;
import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.ParameterResolver;
import com.nealxyc.dryunit.parameter.Resolver;
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
				return null ;
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

				value.setElementType(Object.class);// default element type is
													// object
				getResolvedValue(value, fieldValue);

				List<ParamDefinition> defs = new ArrayList<ParamDefinition>();
				for (ParamValues pv : paramValuesAnno) {
					String id = pv.value();
					ParamDefinition def = new ParamDefinition();
					def.setId(id);
					def.setValue(value);
					defList.add(def);
				}

			}

			return defList.toArray(new ParamDefinition[0]);
		}

		protected static void getResolvedValue(ParamDefinitionValue value,
				Object obj) throws ParamResolveException {
			List<Object> resolvedValue = new ArrayList<Object>();
			if (obj != null) {
				if (obj.getClass().isArray()) {
					// Deal with array
					int length = Array.getLength(obj);
					for (int i = 0; i < length; i++) {
						resolvedValue.add(Array.get(obj, i));
					}
					value.setElementType(obj.getClass().getComponentType());
				} else if (Collection.class.isAssignableFrom(obj.getClass())) {

					Collection c = (Collection) obj;
					resolvedValue.addAll(c);
					if (c.size() > 0) {
						value.setElementType(c.iterator().next().getClass());
					}

				} else if (Iterable.class.isAssignableFrom(obj.getClass())) {
					Iterable itr = (Iterable) obj;
					boolean gotType = false;
					for (Object elem : itr) {
						resolvedValue.add(elem);
						if (!gotType) {
							value.setElementType(elem.getClass());
							gotType = true;
						}
					}
				} else {
					throw new ParamResolveException(
							String.format("Target value is not Collection, Array, or Iterable type"));
				}

			}
			// for null this sets empty collection
			value.setResolvedValues(resolvedValue);
			value.setResolved(true);
		}

		public static ParamDefinition[] getFromMethod(Method method)
				throws ParamResolveException {
			Annotation[] annotations = method.getAnnotations();
			List<ParamValues> paramValuesAnno = new ArrayList<ParamValues>();
			List<ParamDefinition> defList = new ArrayList<ParamDefinition>();

			for (Annotation an : annotations) {
				if (an instanceof ParamValues) {
					paramValuesAnno.add((ParamValues) an);
				}
			}

			if (paramValuesAnno.size() > 0) {
				if (!Modifier.isStatic(method.getModifiers())) {
					return null ;
				}

				if (method.getParameterTypes().length > 0) {
					throw new ParamResolveException(
							"@ParamValues method cannot have parameters.");
				}

				Class<?> returnType = method.getReturnType();
				Object returned = null;
				// //validates return type
				// if (!(returnType.isArray() ||
				// Collection.class.isAssignableFrom(returnType) ||
				// Iterable.class.isAssignableFrom(returnType))) {
				// throw new ParamResolveException(
				// "@ParamValues method cannot have parameters.");
				// }
				ParamDefinitionValue value = new ParamDefinitionValue()
						.setParamResolveType(ParamResolveType.METHOD);
				try {
					returned = method.invoke(null, new Object[0]);

				} catch (IllegalArgumentException e) {
					throw new ParamResolveException(
							String.format(
									"@ParamValues method cannot have parameters: %s.%s",
									method.getClass().getSimpleName(),
									method.getName()));
				} catch (IllegalAccessException e) {
					throw new ParamResolveException(
							String.format(
									"@ParamValues method has to be public: %s.%s",
									method.getClass().getSimpleName(),
									method.getName()));
				} catch (InvocationTargetException e) {
					throw new ParamResolveException(String.format(
							"Error in invoking target method: %s.%s", method
									.getClass().getSimpleName(), method
									.getName()));
				}

				value.setElementType(Object.class);// default element type is
													// object
				getResolvedValue(value, returned);

				List<ParamDefinition> defs = new ArrayList<ParamDefinition>();
				for (ParamValues pv : paramValuesAnno) {
					String id = pv.value();
					ParamDefinition def = new ParamDefinition();
					def.setId(id);
					def.setValue(value);
					defList.add(def);
				}
			}
			
			return defList.toArray(new ParamDefinition[0]);
		}

	}
}
