package com.lakshman.todo.test;

import org.springframework.web.bind.annotation.*;
import com.lakshman.todo.common.services.RedisService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/redis/test")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/save")
    public String save() {
        redisService.save("user", "Lakshman");
        return "Saved in Redis";
    }

    @GetMapping("/get")
    public Object get() {
        return redisService.get("user");
    }

    @DeleteMapping("/delete")
    public String delete() {
        redisService.delete("user");
        return "Deleted";
    }
}