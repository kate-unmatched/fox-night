package com.tsp.foxnight.auth;

import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public final class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(Integer.parseInt("Необходима авторизация.", HttpServletResponse.SC_UNAUTHORIZED));

    }
}
