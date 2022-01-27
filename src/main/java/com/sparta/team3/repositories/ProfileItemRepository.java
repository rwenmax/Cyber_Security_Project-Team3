package com.sparta.team3.repositories;

import com.sparta.team3.entities.ProfileItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileItemRepository extends JpaRepository<ProfileItem, Integer> {
}