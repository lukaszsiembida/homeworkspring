package com.lsiembida.homeworkspring.web;

import com.lsiembida.homeworkspring.domain.user.User;
import com.lsiembida.homeworkspring.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mvc/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView displayRegisterPage() {
        ModelAndView mav = new ModelAndView("register.html");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping
    public String handleUserRegistration(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/mvc/login";
    }
}
