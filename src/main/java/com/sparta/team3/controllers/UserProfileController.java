package com.sparta.team3.controllers;

import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.model.UserDeleteContainer;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Transactional
@RestController
@RequestMapping(value = "/cyberteam3")
public class UserProfileController
{

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "/users/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public List<UserProfile> findTokenAll(@PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            return userProfileRepository.findAll();
        }
        return null;
    }

    @GetMapping(value = "/user/{name}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public UserProfile findUserByName(@PathVariable String name, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            Optional<UserProfile> output = userProfileRepository.findByProfileUsername(name);
            if (output.isEmpty()) {
                return null;
            }
            return output.get();
        }
        return null;
    }

    @PostMapping(value = "/user/add")
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


  
    @DeleteMapping(value = "/user/delete")
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
    @PutMapping(value = "/user/update/{token}" , produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserProfile> updateUser(@RequestBody UserProfile newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            Optional<UserProfile> oldState = userProfileRepository.findById(newState.getId());
            if (oldState.isEmpty()) {
                return new ResponseEntity<UserProfile>((UserProfile) null, HttpStatus.NOT_FOUND);
            }
            userProfileRepository.save(newState);
            return new ResponseEntity<UserProfile>(newState, HttpStatus.OK);
        }
        return new ResponseEntity<UserProfile>((UserProfile) null, HttpStatus.UNAUTHORIZED);
    }
}
