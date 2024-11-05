package com.ureca.sole_paradise.order.db.dto;

import com.ureca.sole_paradise.product.db.dto.ProductDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class OrderDTO {

    private int orderId;
    private LocalDate date;
    private ProductDTO productDTO;
    private int quantity;
}
