package com.example.AT_SBSC.Q1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/status")
    public String status() {
        return "ATIVO";
    }
}
