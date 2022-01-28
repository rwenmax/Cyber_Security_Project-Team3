package com.sparta.team3.repositories;

import com.sparta.team3.entities.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String>
{
    Optional<UserProfile> findByProfileUsername(String username);

    Optional<UserProfile> findById(int user_id);
    void deleteByProfileUsername(String username);
}