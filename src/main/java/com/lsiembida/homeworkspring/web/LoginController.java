package com.lsiembida.homeworkspring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc/login")
public class LoginController {

    @GetMapping
    String displayLoginPage(){
        return "login.html";
    }


}
