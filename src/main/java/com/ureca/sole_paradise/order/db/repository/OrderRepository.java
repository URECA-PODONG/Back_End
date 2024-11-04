package com.ureca.sole_paradise.order.db.repository;

import com.ureca.sole_paradise.order.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUserEntity_UserId(int userId);

}
