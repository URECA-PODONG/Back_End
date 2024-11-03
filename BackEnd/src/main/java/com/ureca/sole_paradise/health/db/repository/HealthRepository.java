package com.ureca.sole_paradise.health.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.sole_paradise.health.db.entity.HealthEntity;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;


public interface HealthRepository extends JpaRepository<HealthEntity, Integer> {

    HealthEntity findFirstByPet(PetEntity pet);

}
