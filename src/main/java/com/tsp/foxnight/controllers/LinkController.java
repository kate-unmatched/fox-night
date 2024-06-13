package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import com.tsp.foxnight.dto.AuthDTO;
import com.tsp.foxnight.dto.LinkDTO;
import com.tsp.foxnight.services.AuthService;
import com.tsp.foxnight.services.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping()
    public PositiveResponse<?> getAllLinks() {
        return Api.positiveResponse(linkService.getLinks());
    }

    @PostMapping()
    public PositiveResponse<?> createLink(@RequestBody @Valid LinkDTO link) {
        return Api.positiveResponse(linkService.createLink(link));
    }

    @DeleteMapping({"{id}"})
    public PositiveResponse<?> deleteLink(@PathVariable Long id) {
        return Api.positiveResponse(linkService.deleteLink(id));
    }
}
