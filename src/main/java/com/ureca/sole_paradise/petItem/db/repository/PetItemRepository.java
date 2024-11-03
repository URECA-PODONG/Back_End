package com.ureca.sole_paradise.petItem.db.repository;

import com.ureca.sole_paradise.petItem.db.entity.PetItemEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetItemRepository extends JpaRepository<PetItemEntity, Integer> {

    PetItemEntity findFirstByUser(UserEntity user);

}