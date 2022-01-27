package com.sparta.team3.repositories;

import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>
{
    Optional<Token> findByToken(String token);
    Optional<Token> findByProfile(UserProfile user_id);
}