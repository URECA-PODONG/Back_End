package com.ureca.sole_paradise.petItem.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.sole_paradise.petItem.db.entity.PetItemCommentEntity;
import com.ureca.sole_paradise.petItem.db.entity.PetItemEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;

public interface PetItemCommentRepository extends JpaRepository<PetItemCommentEntity, Integer>{

    PetItemCommentEntity findFirstByPetItem(PetItemEntity petItem);
    
    PetItemCommentEntity findFirstByUser(UserEntity user);
}
