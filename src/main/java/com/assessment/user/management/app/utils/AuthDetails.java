package com.assessment.user.management.app.utils;

import com.assessment.user.management.app.exceptions.UserNotFoundException;
import com.assessment.user.management.app.model.User;
import com.assessment.user.management.app.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@NoArgsConstructor
public class AuthDetails {


    private UserRepository userRepository;

    @Autowired
    public AuthDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthorizedUser(Principal principal){
        if(principal!=null) {
            final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
            return userRepository.findUserByEmail(currentUser.getUsername()).orElseThrow(
                    () -> new UserNotFoundException(currentUser.getUsername())
            );
        }
        else{
            return  null;
        }
    }
}
