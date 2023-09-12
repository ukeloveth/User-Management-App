package com.assessment.user.management.app.service;



import com.assessment.user.management.app.dto.SignUpDto;
import com.assessment.user.management.app.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

public interface UserService {
    ResponseEntity<UserDto> registerUser(SignUpDto signUpDto) throws MalformedURLException;
}
