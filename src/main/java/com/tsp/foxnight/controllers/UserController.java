package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.services.AuthService;
import com.tsp.foxnight.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public PositiveResponse<?> createNewUser(@RequestBody @Valid UserDTO body, HttpServletRequest request) {
        return Api.positiveResponse(userService.createUser(body));
    }

    @PatchMapping("{id}")
    public PositiveResponse<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO body) {
        return Api.positiveResponse(userService.updateUser(id, body));
    }





}
