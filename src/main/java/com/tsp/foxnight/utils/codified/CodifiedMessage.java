package com.tsp.foxnight.utils.codified;

public interface CodifiedMessage<E extends Number> {
    String getDescription();

    E getCode();

    boolean is(Long id);
}
