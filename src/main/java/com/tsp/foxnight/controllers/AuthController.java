package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationProvider authenticationProvider;

    @PostMapping("auth")
    public PositiveResponse<?> auth(@RequestBody @Valid AuthDTO body, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .authenticated(body.getLogin(), body.getPassword(), Collections.emptyList());
        Authentication authenticate = authenticationProvider.authenticate(token);

//        token.setDetails(Objects.requireNonNull(user));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
//        return Api.positiveResponse(authenticate.getDetails());


        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        return Api.positiveResponse("Вы авторизованы");
    }

}
