package com.tsp.foxnight.controllers;

import com.tsp.foxnight.AuthDTO;
import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.auth.UserDetailsServiceImpl;
import com.tsp.foxnight.entity.User;
import com.tsp.foxnight.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("auth")
    public PositiveResponse<User> auth(@RequestBody @Valid AuthDTO body, HttpServletRequest request, HttpServletResponse response) {
        return Api.positiveResponse(userService.auth(body, request, response));}
}
