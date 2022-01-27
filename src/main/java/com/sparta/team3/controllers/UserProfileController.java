package com.sparta.team3.controllers;

import com.sparta.team3.entities.ProfileItem;
import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.model.UserDeleteJsonObject;
import com.sparta.team3.repositories.ProfileItemRepository;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserProfileController
{
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ProfileItemRepository profileItemRepository;

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
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteJsonObject json)
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
                //delete the profile-item links first
                List<ProfileItem> profileItems = profileItemRepository.findAllByProfile(profile.get());
                profileItemRepository.deleteAll(profileItems);
                //delete token
                tokenRepository.delete(token.get());
                //delete the user from the DB
                userProfileRepository.delete(profile.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }
}
