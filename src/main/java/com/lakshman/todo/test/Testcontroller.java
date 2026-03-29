package com.lakshman.todo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Testcontroller {

    @GetMapping
    public Object get() {
        return "Spring boot application running fine";
    }
}
