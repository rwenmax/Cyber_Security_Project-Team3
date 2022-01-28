package com.sparta.team3.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.team3.entities.Item;
import com.sparta.team3.entities.ProfileItem;
import com.sparta.team3.entities.Token;
import com.sparta.team3.entities.UserProfile;
import com.sparta.team3.model.ItemDeleteJsonObject;
import com.sparta.team3.model.ItemGetJsonObject;
import com.sparta.team3.model.ItemJsonObject;
import com.sparta.team3.model.ItemUpdateJsonObject;
import com.sparta.team3.repositories.ItemRepository;
import com.sparta.team3.repositories.ProfileItemRepository;
import com.sparta.team3.repositories.TokenRepository;
import com.sparta.team3.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//the controller takes the token as input and then performs the respective action only to items linked to that token
@RestController
@RequestMapping(value = "/cyberteam3")
public class ProfileItemController
{
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    ProfileItemRepository profileItemRepository;

    @Autowired
    ObjectMapper mapper;

    @PostMapping(value="/item/add")
    public ResponseEntity<String> addPackage(@RequestBody ItemJsonObject json)
    {
        Optional<Token> token = tokenRepository.findByToken(json.getToken());
        if(token.isEmpty())
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        else
        {
            UserProfile profile = token.get().getProfile();

            //create item entity and save it to the database
            Item item = new Item(json.getItem(), json.getType());

            Optional<Item> result = itemRepository.findByItemName(item.getItemName());

            if(result.isEmpty())
            {
                return new ResponseEntity<>("Item not found", HttpStatus.BAD_REQUEST);
            }
            else
            {
                //link profiles and items through profileitem and add it to the db
                ProfileItem profileItem = new ProfileItem(profile, result.get());
                profileItemRepository.save(profileItem);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping(value= "/item/update")
    public ResponseEntity<String> updatePackage(@RequestBody ItemUpdateJsonObject json)
    {
        Optional<Token> token = tokenRepository.findByToken(json.getToken());

        if(token.isEmpty())
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        else
        {
            UserProfile profile = tokenRepository.findByToken(json.getToken()).get().getProfile();

            Optional<Item> targetItem = itemRepository.findByItemName(json.getTarget());
            if(targetItem.isPresent())
            {
                //get the item to be changed
                Optional<ProfileItem> profileItem = profileItemRepository.findByItemAndProfile(targetItem.get(), profile);

                if(profileItem.isPresent())
                {
                    //delete previous profile-item link
                    profileItemRepository.delete(profileItem.get());

                    //get the item that the user wants
                    Optional<Item> newItem = itemRepository.findByItemName(json.getValue());

                    if(newItem.isPresent())
                    {
                        //create new profile-item link and push it to the database
                        ProfileItem newProfileItem = new ProfileItem(profile, newItem.get());
                        profileItemRepository.save(newProfileItem);
                    }
                    else
                    {
                        return new ResponseEntity<>("Value item not found",HttpStatus.BAD_REQUEST);
                    }
                }
                else
                {
                    return new ResponseEntity<>("Pair not found",HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                return new ResponseEntity<>("Item not found",HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value="/item/get", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Object> getItem(@RequestBody ItemGetJsonObject json){
        Optional<Token> token = tokenRepository.findByToken(json.getToken());

        if(token.isEmpty())
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        else
        {
            //find the profile linked to the token
            UserProfile profile = tokenRepository.findByToken(json.getToken()).get().getProfile();

            //find all profile-item links
            List<ProfileItem> profileItemList = profileItemRepository.findAllByProfile(profile);

            List<Item> itemList = new ArrayList<>();
            for(ProfileItem p : profileItemList)
            {
                //create the list of the items that the user owns
                itemList.add(p.getItem());
            }
            //output the items
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/item/delete")
    public ResponseEntity<String> deleteItem(@RequestBody ItemDeleteJsonObject json)
    {
        Optional<Token> token = tokenRepository.findByToken(json.getToken());

        if(token.isEmpty())
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        else
        {
            //find the profile linked to the token
            UserProfile profile = tokenRepository.findByToken(json.getToken()).get().getProfile();

            //find the item to be deleted
            Optional<Item> item = itemRepository.findByItemName(json.getName());
            if(item.isPresent())
            {
                //find the profile-item link
                Optional<ProfileItem> profileItem = profileItemRepository.findByItemAndProfile(item.get(), profile);

                if(profileItem.isPresent())
                {
                    //delete the link
                    profileItemRepository.delete(profileItem.get());
                }
                else
                {
                    return new ResponseEntity<>("Pair not found",HttpStatus.BAD_REQUEST);
                }
            }
            else
            {
                return new ResponseEntity<>("Item not found",HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "items/delete")
    public ResponseEntity<String> deleteAll(@RequestBody ItemJsonObject json)
    {
        Optional<Token> token = tokenRepository.findByToken(json.getToken());
        if(token.isEmpty())
        {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        else
        {
            //find the profile linked to the token
            UserProfile profile = tokenRepository.findByToken(json.getToken()).get().getProfile();

            //find all the items linked to this profile
            List<ProfileItem> profileItems = profileItemRepository.findAllByProfile(profile);

            //delete all the items linked to this profile
            profileItemRepository.deleteAll(profileItems);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
