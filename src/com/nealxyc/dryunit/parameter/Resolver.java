package com.nealxyc.dryunit.parameter;

public interface Resolver<T, S> {
    public T resolve(S obj) throws Exception;
//    public T resolve(S obj, ResolverState state) throws Exception;
//    
//    public static interface ResolverState{
//    	
//    }
}
