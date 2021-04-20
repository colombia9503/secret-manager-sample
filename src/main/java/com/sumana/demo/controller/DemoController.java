package com.sumana.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private JsonNode secret;

    public DemoController(JsonNode secret) {
        this.secret = secret;
    }

    @GetMapping
    public JsonNode getSecret() {
        return secret;
    }
}
