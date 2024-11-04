package com.ureca.sole_paradise.walkRoute.db.repository;

import com.ureca.sole_paradise.walkRoute.db.entity.WalkRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WalkRouteRepository extends JpaRepository<WalkRouteEntity, Integer> {
    List<WalkRouteEntity> findByUserEntity_UserId(Integer userId);
}