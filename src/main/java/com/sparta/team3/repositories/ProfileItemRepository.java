package com.sparta.team3.repositories;

import com.sparta.team3.entities.Item;
import com.sparta.team3.entities.ProfileItem;
import com.sparta.team3.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileItemRepository extends JpaRepository<ProfileItem, Integer> {

    List<ProfileItem> findAllByProfile(UserProfile profile);
    ProfileItem findByProfile(UserProfile profile);
    Optional<ProfileItem> findByItemAndProfile(Item item, UserProfile profile);
    void deleteAllByProfile(UserProfile profile);
}