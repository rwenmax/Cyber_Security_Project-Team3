package com.sparta.team3.repositories;

import com.sparta.team3.entities.ProfileItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileItemRepository extends JpaRepository<ProfileItem, Integer> {
}