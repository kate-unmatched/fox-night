package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public PositiveResponse<?> auth(@RequestBody @Valid AuthDTO body, HttpServletRequest request) {
        return Api.positiveResponse(authService.login(body, request));
    }

    @GetMapping("refresh-token")
    public PositiveResponse<?> refreshToken(HttpServletRequest request) {
        return Api.positiveResponse(authService.refreshToken(request));
    }




}
