package com.brian.userauthenticationservice.service;


import com.brian.userauthenticationservice.exception.PasswordMismatchException;
import com.brian.userauthenticationservice.exception.UserAlreadyExistsException;
import com.brian.userauthenticationservice.exception.UserNotFoundException;
import com.brian.userauthenticationservice.models.User;
import com.brian.userauthenticationservice.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    UserRepo userRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String login(String email, String password)  {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            if (bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
                return user.get().getId().toString();
            }
            else{
                throw new PasswordMismatchException("password does not match");
            }
        }
        else {
            throw new UserNotFoundException("User not found");
        }

    }

    public String signup(String name,String email, String password) throws UserAlreadyExistsException {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("user with email : "+email+" already exists !!");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setUsername(name);
        userRepo.save(newUser);

        return  newUser.getId().toString();

    }
}
