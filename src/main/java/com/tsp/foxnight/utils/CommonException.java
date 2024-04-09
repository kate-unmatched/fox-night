package com.tsp.foxnight.utils;

import javax.persistence.EntityNotFoundException;
import java.util.function.Supplier;

public class CommonException extends EntityNotFoundException {
    private Class clazz;

    private CommonException(Class clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public String getMessage() {
        return String.format("Объект %s не найден.", clazz.getSimpleName());
    }

    public static Supplier<CommonException> from(Class clazz) {
        return () -> new CommonException(clazz);
    }
}
