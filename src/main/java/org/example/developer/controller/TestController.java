package org.example.developer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/Test")
    public String index() {
        return "Test"; // HTML 파일명
    }

    @GetMapping("/Test2/{universityId}")
    public String index2() {
        return "Test2";
    }
}
