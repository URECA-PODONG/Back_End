package com.ureca.sole_paradise.product.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ureca.sole_paradise.product.db.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
