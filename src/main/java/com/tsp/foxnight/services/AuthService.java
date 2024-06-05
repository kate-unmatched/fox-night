package com.tsp.foxnight.services;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationProvider authenticationProvider;

    public String login(AuthDTO body, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .authenticated(body.getLogin(), body.getPassword(), Collections.emptyList());
        Authentication authenticate = authenticationProvider.authenticate(token);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);

        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        return "Вы авторизованы";
    }
}
