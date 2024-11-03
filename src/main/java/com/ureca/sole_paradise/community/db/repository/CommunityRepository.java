package com.ureca.sole_paradise.community.db.repository;

import com.ureca.sole_paradise.community.db.entity.CommunityEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {

    CommunityEntity findFirstByUser(UserEntity user);

}
