package com.sparta.team3.controllers;

import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cyberteam3")
public class TokenController {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserProfileRepository UserProfileRepository;

    @PostMapping(value = "/token/add/{user_id}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<String> createToken(@PathVariable int user_id) throws NoSuchAlgorithmException
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<UserProfile> optionalProfile = UserProfileRepository.findById(user_id);
        if(optionalProfile.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        UserProfile profile = optionalProfile.get();
        Optional<Token> tokenQuery = tokenRepository.findByProfile(profile);
        if(tokenQuery.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        String sToken = generateToken(profile.getProfileUsername());
        Token token = new Token(sToken, profile);
        tokenRepository.save(token);

        return new ResponseEntity<>(sToken, HttpStatus.OK);
    }

    private String generateToken(String email) throws NoSuchAlgorithmException {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);

        Key secretKey = generator.generateKey();

        JwtBuilder builder = Jwts.builder().setSubject(email).signWith(signatureAlgorithm, secretKey);

        return builder.compact();
    }

    @GetMapping(value = "/tokens/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<List<Token>> findTokenAll(@PathVariable String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            return new ResponseEntity<>(tokenRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/token/findbytoken/{token}/{tokenA}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<Token> findTokenByToken(@PathVariable String token, @PathVariable String tokenA) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<Token> tokenResult = tokenRepository.findByToken(tokenA);
        if (tokenResult.isPresent()) {
            Optional<Token> output = tokenRepository.findByToken(token);
            if (output.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(output.get(), HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping(value = "/token/deletebyid/{id}/{tokenA}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<Integer> deleteTokenById(@PathVariable Integer id, @PathVariable String tokenA) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<Token> tokenResult = tokenRepository.findByToken(tokenA);
        if (tokenResult.isPresent()) {
            Optional<Token> token = tokenRepository.findById(id);
            if (token.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else
                tokenRepository.deleteById(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "/token/deletebytoken/{token}/{tokenA}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<String> deleteByToken(@PathVariable String token, @PathVariable String tokenA)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<Token> tokenResult = tokenRepository.findByToken(tokenA);
        if (tokenResult.isPresent()) {
            Optional<Token> tokenEntity = tokenRepository.findByToken(token);
            if (tokenEntity.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                tokenRepository.deleteById(tokenEntity.get().getId());
            }
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
