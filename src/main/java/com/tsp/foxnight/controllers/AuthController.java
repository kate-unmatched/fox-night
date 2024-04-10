package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.dto.UserOfficerDTO;
import com.tsp.foxnight.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;

    @PostMapping("auth")
    public PositiveResponse<?> auth(@RequestBody @Valid AuthDTO body, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .authenticated(body.getLogin(), body.getPassword(), Collections.emptyList());
        Authentication authenticate = authenticationProvider.authenticate(token);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);


        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        return Api.positiveResponse("Вы авторизованы");
    }

    @PostMapping("register-officer")
    public PositiveResponse<?> register(@RequestBody @Valid UserOfficerDTO body, HttpServletRequest request){
        return Api.positiveResponse(userService.createUser(body));
    }

    @GetMapping("secret")
    public PositiveResponse<?> secret(HttpServletRequest request){
        return Api.positiveResponse(userService.getSecretUser());
    }

    @DeleteMapping("officer")
    public PositiveResponse<?> deleteOfficer(@RequestParam Long officerId, HttpServletRequest request){
        return Api.positiveResponse(userService.deleteUser(officerId));
    }



}
