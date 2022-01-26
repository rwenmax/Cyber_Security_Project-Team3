package com.sparta.team3.repositories;

import com.sparta.team3.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer>
{
    Optional<Token> findByToken(String token);
    Optional<Token> findByProfileUserName(String user_id);
}