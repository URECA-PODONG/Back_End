package com.ureca.sole_paradise.missing.db.repository;

import com.ureca.sole_paradise.walkRoute.db.entity.WalkRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.sole_paradise.missing.db.entity.MissingEntity;
import com.ureca.sole_paradise.pet.db.entity.PetEntity;

public interface MissingRepository extends JpaRepository<MissingEntity, Integer> {
	
	   MissingEntity findFirstByPet(PetEntity pet);

	   MissingEntity findFirstByWalkroute(WalkRouteEntity walkRoute);


}
