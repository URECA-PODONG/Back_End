package com.ureca.sole_paradise.product.db.entity;


import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, updatable = false)
    private Integer productId;

    @Column(nullable = false)
    private String productTitle;

    private String productLink;

    private String productImage;

    @Column(nullable = false)
    private int productLprice;

    private int productHprice;

    private String productMallName;

    private int productType;

    private String productBrand;

    private String productMaker;

    private String productCategory1;

    private String productCategory2;

    private String productCategory3;

    private String productCategory4;
}
