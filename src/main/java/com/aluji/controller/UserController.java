package com.aluji.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @RequestMapping("/login")
    public Object login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        if ("1".equals(password)) {
            result.put("status", true);
            result.put("msg", "ok");
        } else {
            result.put("status", false);
            result.put("msg", "error");
        }
        return result;
    }
}
