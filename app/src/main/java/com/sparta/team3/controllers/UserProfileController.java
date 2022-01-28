package com.sparta.team3.controllers;

import com.sparta.team3.entities.ProfileItem;
import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.model.UserDeleteJsonObject;
import com.sparta.team3.repositories.ProfileItemRepository;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> findTokenAll(@PathVariable String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            List<UserProfile> users = userProfileRepository.findAll();
            return new ResponseEntity<>(users,headers, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Null",headers,HttpStatus.OK);
    }
    @GetMapping(value = "/user/{name}/{token}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<?> findUserByName(@PathVariable String name, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (tokenResult.isPresent()) {
            Optional<UserProfile> output = userProfileRepository.findByProfileUsername(name);
            if (output.isEmpty()) {
                return new ResponseEntity<>("Null", headers,HttpStatus.OK);
            }
            return new ResponseEntity<>(output.get(), headers,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, headers,HttpStatus.OK);
    }
    @PostMapping(value = "/user/add")
    public ResponseEntity<String> createNewUser(@RequestBody UserProfile credentials) {

        Optional<UserProfile> profile = userProfileRepository.findByProfileUsername(credentials.getProfileUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
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
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("User already exists",headers, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/user/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteJsonObject json)
    {
        Optional<UserProfile> profile = userProfileRepository.findByProfileUsername(json.getUserName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if(profile.isEmpty())
        {
            return new ResponseEntity<>("User does not exist",headers, HttpStatus.BAD_REQUEST);
        }
        else
        {
            Optional<Token> token = tokenRepository.findByToken(json.getToken());
            if(token.isEmpty())
            {
                return new ResponseEntity<>("Invalid token", headers,HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(headers,HttpStatus.OK);
            }
        }
    }
    @PutMapping(value = "/user/update/{token}" , produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserProfile> updateUser(@RequestBody UserProfile newState, @PathVariable String token) {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (tokenResult.isPresent()) {
            Optional<UserProfile> oldState = userProfileRepository.findById(newState.getId());
            if (oldState.isEmpty()) {
                return new ResponseEntity<UserProfile>((UserProfile) null, headers, HttpStatus.NOT_FOUND);
            }
            userProfileRepository.save(newState);
            return new ResponseEntity<UserProfile>(newState,headers, HttpStatus.OK);
        }
        return new ResponseEntity<UserProfile>((UserProfile) null,headers, HttpStatus.UNAUTHORIZED);
    }
    @GetMapping(value = "/login/{name}/{pass}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE, })
    public ResponseEntity<?> userLogin(@PathVariable String name, @PathVariable String pass) {
        Optional<UserProfile> output = userProfileRepository.findByProfileUsername(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        if (output.isEmpty()) {
            return new ResponseEntity<UserProfile>((UserProfile) null,headers, HttpStatus.OK);
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
            return new ResponseEntity<>(output.get(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }
}
