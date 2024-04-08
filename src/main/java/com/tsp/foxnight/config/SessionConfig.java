package com.tsp.foxnight.config;

import org.springframework.lang.Nullable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class SessionConfig extends ConcurrentSessionControlAuthenticationStrategy {
    private final JdbcIndexedSessionRepository repository;
    private final Integer maxSessionsCount;

    public SessionConfig(JdbcIndexedSessionRepository repository,
                         SessionRegistry sessionRegistry,
                         Integer maxSessionsCount) {
        super(sessionRegistry);
        this.repository = repository;
        this.maxSessionsCount = maxSessionsCount;
    }

    public boolean userCountExceeded(String login, HttpSession session) {
        Map<String, ? extends Session> map = repository.findByPrincipalName(login.toLowerCase());

        String sessionId = safeGet(session, HttpSession::getId);
        Session expectedSession = map.remove(sessionId);
        if (Objects.nonNull(expectedSession) && !expectedSession.isExpired()) return false; // релогин той же сессии

        List<String> expired = filter(map.values(), Session::isExpired, Session::getId);
        return map.size() - expired.size() >= maxSessionsCount;
    }

    @Nullable
    public static <E, V> V safeGet(@Nullable E object, Function<E, V> converter) {
        return Optional.ofNullable(object).map(converter).orElse(null);
    }

    public static <T, R> List<R> filter(Collection<T> source, Predicate<T> predicate, Function<T, R> extractor) {
        return extract(source, Function.identity(), predicate, extractor, toList());
    }

    public static <T, R, E, C extends Iterable<E>> C extract(Collection<T> source, Function<T, R> extractor,
                                                             Predicate<R> valuePredicate, Function<R, E> adapter,
                                                             Collector<E, ?, C> collector) {
        return emptyIfNull(source).stream().map(extractor).filter(valuePredicate).map(adapter).collect(collector);
    }
}
