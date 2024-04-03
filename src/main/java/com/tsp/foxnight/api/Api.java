package com.tsp.foxnight.api;

public class Api {
    public static <T> PositiveResponse<T> positiveResponse(T data) {
        return new PositiveResponse<>(data);
    }
    public static PositiveResponse<Object> emptyPositiveResponse() {
        return new PositiveResponse<>(null);
    }

    public static NegativeResponse<String> negativeResponse(String code, String errorMessage, Object details) {
        return new NegativeResponse<>(code, errorMessage, details);
    }
}
