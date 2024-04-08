package com.tsp.foxnight.controllers;

import com.tsp.foxnight.api.Api;
import com.tsp.foxnight.api.PositiveResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fox")
@Tag(name = "Hello API", description = "Rav")
public class HelloController {

    @GetMapping("")
    public PositiveResponse<String> sayHello(){
        return Api.positiveResponse("Hello, dear Kate!");
    }
}
