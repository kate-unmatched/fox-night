package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.utils.Errors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    private static final String DEF_MSG = "Системная ошибка";

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Throwable t) {
        Throwable cause = Optional.ofNullable(t.getCause()).orElse(t);
        return Api.negativeResponse("500",
                defaultString(cause.getMessage(), DEF_MSG),
                ExceptionUtils.getStackTrace(cause)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleException(MissingServletRequestParameterException t) {
        return Api.negativeResponse(Errors.E707.name(),
                String.format(Errors.E707.getDescription(), t.getParameterName()),
                ExceptionUtils.getStackTrace(t)
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Errors.CodifiedException.class)
    public Object handleException(Errors.CodifiedException t) {
        return Api.negativeResponse(t.getError().name(),
                t.getMsg(), ExceptionUtils.getStackTrace(t)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException t) {
        FieldError error = t.getBindingResult().getFieldError();
        return Api.negativeResponse(Errors.E707.name(),
                String.format(Errors.E707.getDescription(),
                        Objects.requireNonNull(error).getField()),
                ExceptionUtils.getStackTrace(t)
        );
    }
}
