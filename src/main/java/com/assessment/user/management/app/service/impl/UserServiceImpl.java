package com.assessment.user.management.app.service.impl;
import com.assessment.user.management.app.OAuthConfig.CustomOAuth2User;
import com.assessment.user.management.app.dto.SignUpDto;
import com.assessment.user.management.app.dto.UserDto;
import com.assessment.user.management.app.enums.Provider;
import com.assessment.user.management.app.exceptions.InvalidPasswordException;
import com.assessment.user.management.app.exceptions.UserAlreadyExistException;
import com.assessment.user.management.app.exceptions.UserNotFoundException;
import com.assessment.user.management.app.model.User;
import com.assessment.user.management.app.repository.UserRepository;
import com.assessment.user.management.app.security.jwt.JWTTokenProvider;
import com.assessment.user.management.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<UserDto> registerUser(SignUpDto signUpDto) throws MalformedURLException {
        System.out.println("i am in the service "+signUpDto);
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new UserAlreadyExistException("User with email already exist ");
        }
        String newPassword=signUpDto.getPassword();

        if (newPassword.length() < 8 || newPassword.length() > 16) {
            throw new InvalidPasswordException("Password must be between 8 and 16 characters");
        }
        User user = new User();
        System.out.println(user+"i am the user");
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setProvider(Provider.LOCAL);

        User createdUser = userRepository.save(user);
        System.out.println(createdUser);
        UserDto userDto = new UserDto();
        userDto.setUserId(createdUser.getId());
        userDto.setEmail(createdUser.getEmail());
        userDto.setName(createdUser.getName());
        userDto.setPassword(createdUser.getPassword());

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    public void processOAuthUser(CustomOAuth2User user, Authentication authentication) {
        Optional<User> existUser = userRepository.findUserByEmail(user.getEmail());
        if(existUser.isEmpty()) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setProvider(Provider.GOOGLE);
            newUser.setPassword(passwordEncoder.encode(user.getName()));
            userRepository.save(newUser);
        }
        String token = jwtTokenProvider.generateToken(authentication);
    }




    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }


        public void processOAuthPostLogin(String username) {
            User existUser = userRepository.getUserByUsername(username);
            if (existUser == null) {
                User newUser = new User();
                newUser.setName(username);
                newUser.setProvider(Provider.GOOGLE);
                userRepository.save(newUser);


            }

        }

}


