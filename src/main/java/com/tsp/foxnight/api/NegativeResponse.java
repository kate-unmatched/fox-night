package com.tsp.foxnight.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NegativeResponse<T> extends Response {

    protected final T code;

    private final String message;

    private final Object details;

    NegativeResponse(T code, String message, Object details) {
        super(Boolean.FALSE);
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
