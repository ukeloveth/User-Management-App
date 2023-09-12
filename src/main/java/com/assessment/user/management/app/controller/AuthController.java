package com.assessment.user.management.app.controller;

import com.assessment.user.management.app.dto.LoginDto;
import com.assessment.user.management.app.response.LoginResponse;
import com.assessment.user.management.app.security.jwt.JWTTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/auth")
@AllArgsConstructor
public class AuthController {
    private  final JWTTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginDto loginDto, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        System.out.println(authentication+"=====================");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token+"print out token here");

//        User user = userService.getUserByEmail(loginDto.getEmail());
        session.setAttribute("loggedUserEmail", loginDto.getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping(value="/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}

