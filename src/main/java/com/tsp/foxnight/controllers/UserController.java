package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.UserCreateDTO;
import com.tsp.foxnight.dto.UserUpdateDTO;
import com.tsp.foxnight.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(consumes = "multipart/form-data")
    public PositiveResponse<?> createNewUser(@RequestPart("data") @Valid UserCreateDTO body,
                                             @RequestPart("photo") MultipartFile photo) {
        return Api.positiveResponse(userService.createUser(body, photo));
    }

    @PostMapping(value = "{id}", consumes = "multipart/form-data")
    public PositiveResponse<?> updateUserPhoto(@RequestPart("data") @PathVariable @Valid Long id,
            @RequestPart("photo") MultipartFile photo) {
        return Api.positiveResponse(userService.updateUserPhoto(id, photo));
    }

    @PostMapping("{id}")
    public PositiveResponse<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO body) {
        return Api.positiveResponse(userService.updateUser(id, body));
    }

    @DeleteMapping("{id}")
    public PositiveResponse<?> deleteUser(@PathVariable Long id) {
        return Api.positiveResponse(userService.deleteUser(id));
    }
}
