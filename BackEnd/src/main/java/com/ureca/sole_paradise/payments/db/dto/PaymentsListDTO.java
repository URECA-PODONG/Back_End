package com.ureca.sole_paradise.payments.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsListDTO {

    private int merchantId;
    private String approvalNumber;
    private String cardName;
    private String cardNumber;
    private LocalDateTime createdAt;
    private String impUid;
    private int installmentMonths;
    private int payAmount;
    private String payMethod;
    private String payName;
    private String payStatus;
    private String pg;
    private LocalDateTime updatedAt;
    private int userId;

}
