package com.tsp.foxnight.utils.codified;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.tsp.foxnight.utils.codified.CodifiedEnum.*;
import static java.util.Optional.ofNullable;

@Getter
public class CodifiedError extends RuntimeException {
    private CodifiedEnum codifiedMessage;
    private String arg;

    private CodifiedError(CodifiedEnum message) {
        super(message.getDescription());
        this.codifiedMessage = message;
    }

    private CodifiedError(CodifiedEnum message, Object... args) {
        super(String.format(message.getDescription(), args));
        this.codifiedMessage = message;
    }

    private static final BiConsumer<Boolean, CodifiedError> errorBiConsumer = (flag, codifiedError) -> {
        if (flag) {
            throw codifiedError;
        }
    };

    public static void check(@NotNull Boolean bool, CodifiedError error) {
        errorBiConsumer.accept(!bool, error);
    }

    public static <T> void check(Predicate<T> predicate, T object, CodifiedError error) {
        ofNullable(object).ifPresent(o -> check(predicate.test(o), error));
    }

    public static CodifiedError get500() {
        return new CodifiedError(E500);
    }

    public static CodifiedError get616() {
        return new CodifiedError(E616);
    }

    public static CodifiedError get603() {
        return new CodifiedError(E603);
    }

    public static CodifiedError get606() { return new CodifiedError(E606); }

    public static void throw606() { throw get606(); }

    public static void throw603() {
        throw get603();
    }

    public static void throw612() {
        throw get612();
    }

    public static void throw614() {
        throw get614();
    }

    public static void throw615() {
        throw get615();
    }

    public static void throw616() {
        throw get616();
    }

    public static void throw611(Object... args) {
        throw get611(args);
    }

    public static void throw613(Object... args) {
        throw get613(args);
    }

    public static void throw617(Object... args) {
        throw get617(args);
    }

    public static void throw618(Object... args) {
        throw get618(args);
    }

    public static CodifiedError get607() { return new CodifiedError(E607); }

    public static CodifiedError get610() {
        return new CodifiedError(E610);
    }

    public static CodifiedError get611(Object... args) {
        return new CodifiedError(E611, args);
    }

    public static CodifiedError get613(Object... args) {
        return new CodifiedError(E613, args);
    }

    public static CodifiedError get612() { return new CodifiedError(E612); }

    public static CodifiedError get614() { return new CodifiedError(E614); }

    public static CodifiedError get615() { return new CodifiedError(E615); }

    public static CodifiedError get617(Object... args) {
        return new CodifiedError(E617, args);
    }

    public static CodifiedError get618(Object... args) {
        return new CodifiedError(E618, args);
    }

    public static CodifiedError get701() {
        return new CodifiedError(E701);
    }

    public static CodifiedError get705() {
        return new CodifiedError(E705);
    }

    public static void throw701() {
        throw get701();
    }

    public static void throw708() {
        throw get708();
    }

    public static CodifiedError get708() {
        return new CodifiedError(E708);
    }
}
