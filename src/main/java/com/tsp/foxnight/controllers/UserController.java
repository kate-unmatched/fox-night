package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.dto.UserBriefDTO;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.services.AuthService;
import com.tsp.foxnight.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("{id}")
    public PositiveResponse<?> getUser(@PathVariable Long id) {
        return Api.positiveResponse(userService.getUser(id));
    }
    @GetMapping()
    public PositiveResponse<?> getAllUsers() {
        return Api.positiveResponse(userService.getAllUsers());
    }
    @PostMapping()
    public PositiveResponse<?> createNewUser(@RequestBody @Valid UserDTO body, HttpServletRequest request) {
        return Api.positiveResponse(userService.createUser(body));
    }

    @PutMapping("{id}")
    public PositiveResponse<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO body) {
        return Api.positiveResponse(userService.updateUser(id, body));
    }

    @DeleteMapping("{id}")
    public PositiveResponse<?> deleteUser(@PathVariable Long id) {
        return Api.positiveResponse(userService.deleteUser(id));
    }





}
