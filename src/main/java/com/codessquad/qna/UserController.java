package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String create(String userId, String password, String name, String email) {
        System.out.println(" userId : " + userId +
                " password : " + password +
                " name : " + name +
                " email : " + email);
        return "index";
    }
}
