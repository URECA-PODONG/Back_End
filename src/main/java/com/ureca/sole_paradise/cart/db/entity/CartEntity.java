package com.ureca.sole_paradise.cart.db.entity;

import com.ureca.sole_paradise.product.db.entity.ProductEntity;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cart")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false, updatable = false)
    private Integer cartId;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}

