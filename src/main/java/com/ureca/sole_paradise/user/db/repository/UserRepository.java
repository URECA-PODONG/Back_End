package com.ureca.sole_paradise.user.db.repository;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
    Optional<UserEntity> findByAccountEmail(String email); // Optional로 반환 타입 변경
}
