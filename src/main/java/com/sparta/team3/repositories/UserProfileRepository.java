package com.sparta.team3.repositories;

import com.sparta.team3.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String>
{

}
