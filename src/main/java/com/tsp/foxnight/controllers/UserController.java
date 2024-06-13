package com.tsp.foxnight.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @SneakyThrows
    @GetMapping("{id}")
    public PositiveResponse<?> getUser(@PathVariable Long id) {
        return Api.positiveResponse(userService.getUser(id));
    }
    @GetMapping("birthdays")
    public PositiveResponse<?> getUpcomingBirthdays() {
        return Api.positiveResponse(userService.getBirthdays());
    }
    @GetMapping()
    public PositiveResponse<?> getAllUsers() {
        return Api.positiveResponse(userService.getAllUsers());
    }
    @PostMapping()
    public PositiveResponse<?> createNewUser(@RequestBody @Valid UserDTO body, HttpServletRequest request) {
        return Api.positiveResponse(userService.createUser(body));
    }

    @PatchMapping("{id}")
    public PositiveResponse<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO body) {
        return Api.positiveResponse(userService.updateUser(id, body));
    }

    @DeleteMapping("{id}")
    public PositiveResponse<?> deleteUser(@PathVariable Long id) {
        return Api.positiveResponse(userService.deleteUser(id));
    }
}
