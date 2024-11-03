package com.ureca.sole_paradise.payments.db.repository;

import com.ureca.sole_paradise.payments.db.entity.PaymentsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Integer> {

//    PaymentsEntity findFirstByOrder(Order order);

    Page<PaymentsEntity> findByUser_UserId(int userId, Pageable pageable);

}