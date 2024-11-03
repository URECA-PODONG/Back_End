package com.ureca.sole_paradise.pet.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.sole_paradise.pet.db.entity.PetEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;

public interface PetRepository extends JpaRepository <PetEntity, Integer> {
	PetEntity findFirstByUser(UserEntity user);
}
