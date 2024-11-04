package com.ureca.sole_paradise.cart.db.repository;

import com.ureca.sole_paradise.cart.db.entity.CartEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    List<CartEntity> findByUserEntity(UserEntity userEntity);
}
