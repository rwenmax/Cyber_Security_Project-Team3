package com.sparta.team3.repositories;

import com.sparta.team3.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository <Token, Integer>
{
}
