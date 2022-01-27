package com.sparta.team3.controllers;

import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.model.UserDeleteContainer;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Transactional
@RestController
@RequestMapping(value = "/user")
public class UserProfileController
{

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping(value = "/new")
    public ResponseEntity<String> createNewUser(@RequestBody UserProfile credentials) {

        Optional<UserProfile> profile = userProfileRepository.findByProfileUsername(credentials.getProfileUsername());

        if(profile.isEmpty())
        {
            //TODO add hashing for password
            userProfileRepository.save(credentials);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteContainer json)
    {
        Optional<UserProfile> profile = userProfileRepository.findByProfileUsername(json.getUserName());
        if(profile.isEmpty())
        {
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }
        else
        {
            Optional<Token> token = tokenRepository.findByToken(json.getToken());
            if(token.isEmpty())
            {
                return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
            }
            else
            {
                tokenRepository.delete(token.get());
                userProfileRepository.deleteByProfileUsername(json.getUserName());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }
}
