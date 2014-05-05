package com.nealxyc.dryunit.parameter.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.nealxyc.dryunit.parameter.ParamRef;
import com.nealxyc.dryunit.parameter.ParamResolveException;
import com.nealxyc.dryunit.parameter.ParamTest;
import com.nealxyc.dryunit.parameter.ParamTest.Mode;
import com.nealxyc.dryunit.parameter.ParamValues;
import com.nealxyc.dryunit.parameter.ParameterResolver;
import com.nealxyc.dryunit.parameter.Resolver;

public class ParameterResolverImpl implements ParameterResolver {

	private HashMap<String, Collection<Object>> paramsMap  = new HashMap<String, Collection<Object>>();
	private HashMap<String, ParamDefinition> paramsDefMap  = new HashMap<String, ParamDefinition>();
	
	@Override
	public Collection<Object> resolve(ParamRef ref) throws ParamResolveException {
		ParamDefinition def = paramsDefMap.get(ref.getId());
		if(def == null){
			throw new ParamResolveException(String.format("@%s with id '%s' is not found", ParamValues.class.getSimpleName(), ref.getId()));
		}
		
		if(def.getValue().isResolved()){
			return def.getValue().getResolvedValues();
		}else{
			Resolver resolver = def.getValue().getParamResolver();
			if(resolver instanceof MethodResolver){
				MethodResolver mr = (MethodResolver) resolver ;
				return mr.resolve(this);
			}else{
				throw new ParamResolveException(String.format("Cannot resolve parameter id='@%s'", ref.getId()));
			}
		}
	}

	public void addParamDefinition(ParamDefinition def) {
		paramsDefMap.put(def.getId(), def);
	}

	public void scan(Class<?> cls) throws ParamResolveException {
	    Field[] fields = cls.getFields();
	    for(Field field: fields){
	    	ParamDefinition[] defs = ParamDefinition.Helper.getFromField(field);
	    	for(ParamDefinition def: defs){
	    		this.addParamDefinition(def);
	    	}
	    }
	    
	    //TODO Methods
	}

	@Override
	public Collection<Object[]> resolve(ParamTest testAnno, ParamRef... refs) throws ParamResolveException {
		Collection<Object[]> computedParams = new ArrayList<Object[]>();
		if(refs.length == 0){
			return computedParams ;
		}
		Collection<Object>[] params = new Collection[refs.length];
		for(int i = 0 ; i < params.length; i ++){
			params[i] = resolve(refs[i]);
		}
		Mode paramCombinationMode = Mode.GROUP ;
		if(testAnno != null){
			paramCombinationMode = testAnno.mode() ;
		}
		
		Iterator[] itrs = new Iterator[params.length];
		switch(paramCombinationMode){
			case GROUP:
				//verify all elements in params have the same length;
				int size = params[0].size();
				for(int i = 0 ; i < params.length; i ++){
					if(params[i].size() != size){
						throw new ParamResolveException(String.format("Parameter values for '%s' and '%s' are different in length.", refs[0].getId(), refs[i].getId()));
					}
					
				}
				
				for(int j = 0 ; j < params.length; j ++){
					itrs[j] = params[j].iterator() ;
				}
				for(int i = 0 ; i < size; i ++){
					Object[] param = new Object[params.length];
					for(int j = 0 ; j < params.length; j ++){
						param[j] = itrs[j].next() ;
					}
					computedParams.add(param);
				}
				break;
			case PERMUTATION:
				Object[] param = new Object[params.length];//contains the very first set of parameters
				for(int j = 0 ; j < params.length; j ++){
					itrs[j] = params[j].iterator() ;
					param[j] = itrs[j].next();
				}
				int movingIdx = 0 ;
				computedParams.add(param);
				while(hasNext(itrs) && movingIdx < itrs.length){
					while(itrs[movingIdx].hasNext()){
						param = Arrays.copyOf(param, params.length);
						param[movingIdx] = itrs[movingIdx].next() ;//permutates the [movingIdx]th parameter object
						computedParams.add(param);
					}
					movingIdx ++ ;//[movingIdx]th iterator reaches the end, move to [movingIdx + 1]th
				}
				
				break ;
		}
		
		return computedParams ;
	}
	
	/**
	 * Returns true if <b>any</b> Iterator in itrs has next object
	 * @param itrs
	 * @return
	 */
	private boolean hasNext(Iterator... itrs){
		for(Iterator itr: itrs){
			if(itr.hasNext()){
				return true ;
			}
		}
		return false ;
	}
	

}
