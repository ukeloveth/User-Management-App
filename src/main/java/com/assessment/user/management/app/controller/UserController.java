package com.assessment.user.management.app.controller;

import com.assessment.user.management.app.dto.SignUpDto;
import com.assessment.user.management.app.dto.UserDto;
import com.assessment.user.management.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.MalformedURLException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignUpDto signUpDto) throws MalformedURLException {
        ResponseEntity<UserDto> responseEntity = userService.registerUser(signUpDto);
        HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();
        UserDto userDto = responseEntity.getBody();
        return new ResponseEntity<>(userDto, httpStatus);
    }
}
