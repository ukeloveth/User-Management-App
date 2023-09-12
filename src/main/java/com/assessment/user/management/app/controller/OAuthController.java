package com.assessment.user.management.app.controller;

import com.assessment.user.management.app.OAuthConfig.CustomOAuth2User;
import com.assessment.user.management.app.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class OAuthController {
    private UserServiceImpl userService;

    @Autowired
    public OAuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String socialRegister(HttpSession session) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"**************");

        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            System.out.println(oAuth2User+"%%%%%%%%%%%%%%%%");

            String loggedUserEmail = oAuth2User.getEmail();
            System.out.println(loggedUserEmail);
            session.setAttribute("loggedUserEmail", loggedUserEmail);

            String name = authentication.getName();
            return name + " successfully logged in";
        } else {
            return "User is not authenticated with CustomOAuth2User";
        }
    }

}
