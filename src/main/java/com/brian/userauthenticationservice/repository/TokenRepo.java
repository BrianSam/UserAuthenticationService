package com.brian.userauthenticationservice.repository;

import com.brian.userauthenticationservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);

    Token save(Token token);

}
