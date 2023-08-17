package org.example.developer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TtsController {

    @GetMapping("/")
    public String index() {
        return "index"; // 이 부분은 해당하는 템플릿 파일의 이름을 반환합니다.
    }
}
