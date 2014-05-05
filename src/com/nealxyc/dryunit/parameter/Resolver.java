package com.nealxyc.dryunit.parameter;

public interface Resolver<T, S> {
    public T resolve(S obj);
}
