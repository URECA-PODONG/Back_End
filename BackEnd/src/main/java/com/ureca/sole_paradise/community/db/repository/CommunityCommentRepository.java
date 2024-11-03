package com.ureca.sole_paradise.community.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ureca.sole_paradise.community.db.entity.CommunityCommentEntity;
import com.ureca.sole_paradise.community.db.entity.CommunityEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;


public interface CommunityCommentRepository extends JpaRepository<CommunityCommentEntity, Integer>{

	CommunityCommentEntity findFirstByPost(CommunityEntity communityEntity);

	CommunityCommentEntity findFirstByUser(UserEntity user);

}
