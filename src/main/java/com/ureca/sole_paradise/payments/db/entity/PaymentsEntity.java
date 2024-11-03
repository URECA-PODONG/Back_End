package com.ureca.sole_paradise.payments.db.entity;

import com.ureca.sole_paradise.user.db.entity.UserEntity;
import com.ureca.sole_paradise.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "payments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id", nullable = false, updatable = false)
    private Integer merchantId;

    private String pg;
    private Integer payAmount;
    private String payMethod;
    private String payName;

    private String impUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Setter
    private String payStatus;

    private String cardName;
    private String cardNumber;
    private int installmentMonths;
    private String approvalNumber;
}
