package com.sparta.team3.controllers;

import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/login")
public class LoginController
{
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping(value = "/new")
    public String createNewUser(@RequestBody UserProfile credentials) throws NoSuchAlgorithmException {
        userProfileRepository.save(credentials);

        String sToken = generateToken(credentials.getProfileUsername());
        Token token = new Token(sToken, credentials);
        tokenRepository.save(token);

        return sToken;
    }

    @GetMapping(value = "/show")
    public UserProfile createNewUser(@RequestParam int id)  {
        Optional<UserProfile> result = userProfileRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    private String generateToken(String username) throws NoSuchAlgorithmException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key secretKey = generator.generateKey();

        JwtBuilder builder = Jwts.builder().setSubject(username).signWith(signatureAlgorithm, secretKey);

        return builder.compact();
    }
}
