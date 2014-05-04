package com.nealxyc.dryunit.parameter;

public interface Resolver<T> {
    public T resolve(Object obj);
}
