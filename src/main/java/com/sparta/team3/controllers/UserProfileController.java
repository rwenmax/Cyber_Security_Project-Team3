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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.MessageDigestSpi;

import java.security.NoSuchAlgorithmException;
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

    @Autowired
    private ProfileItemRepository profileItemRepository;
  
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
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] bytesOfPass = credentials.getProfilePassword().getBytes(StandardCharsets.UTF_8);
                byte[] thedigest = md.digest(bytesOfPass);
                credentials.setProfilePassword(new String(thedigest, StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            userProfileRepository.save(credentials);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/user/delete")
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
                //tokenRepository.delete(token.get());
                //delete the user from the DB
                userProfileRepository.delete(profile.get());
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
    @GetMapping(value = "/login/{name}/{pass}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public UserProfile userLogin(@PathVariable String name, @PathVariable String pass) {
        Optional<UserProfile> output = userProfileRepository.findByProfileUsername(name);
        if (output.isEmpty()) {
            return null;
        }
        String newPass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytesOfPass = pass.getBytes(StandardCharsets.UTF_8);
            byte[] thedigest = md.digest(bytesOfPass);
            newPass = new String(thedigest, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (output.get().getProfilePassword().equals(newPass)){
            return output.get();
        }
        return null;
    }
}
