package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.UserCreateDTO;
import com.tsp.foxnight.dto.UserDTO;
import com.tsp.foxnight.services.RestAuditService;
import com.tsp.foxnight.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("rest-audit")
@RequiredArgsConstructor
public class RestAuditController {
    private final RestAuditService restAuditService;
    @GetMapping
    public PositiveResponse<?> getRestRequests() {
        return Api.positiveResponse(restAuditService.getAllRequests());
    }
}
