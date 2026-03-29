package com.platzi.play.web.controller;

import com.platzi.play.domain.service.PlatziPlayAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final PlatziPlayAIService aiService;
    private final String platform;

    public HelloController(PlatziPlayAIService aiService,
                           @Value("${spring.application.name}") String platform){
        this.aiService = aiService;
        this.platform = platform;
    }

    @GetMapping("/hello")
    public String hello(){

        return this.aiService.generateGreeting(this.platform);
    }
}
