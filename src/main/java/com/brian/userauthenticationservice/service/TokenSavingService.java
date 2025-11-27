package com.brian.userauthenticationservice.service;

import com.brian.userauthenticationservice.models.Token;
import com.brian.userauthenticationservice.models.User;
import com.brian.userauthenticationservice.repository.TokenRepo;
import com.brian.userauthenticationservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenSavingService {

    public TokenSavingService(TokenRepo tokenRepository, UserRepo userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    private TokenRepo tokenRepository;
    private UserRepo userRepository;

    public void saveToken(String username, String tokenValue, Instant expiry) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Token token = new Token();
        token.setValue(tokenValue);
        token.setExpiryDate(Date.from(expiry));
        token.setUser(user);

        tokenRepository.save(token);
    }
}
