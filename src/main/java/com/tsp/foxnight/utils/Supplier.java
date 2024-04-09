package com.tsp.foxnight.utils;

@FunctionalInterface
public interface Supplier<T, E extends Throwable> {
    T get() throws E;
}
