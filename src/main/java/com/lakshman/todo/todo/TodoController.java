package com.lakshman.todo.todo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/todos")
public class TodoController {

    @GetMapping

    public String hello() {
        return "I m running fine";
    }
}