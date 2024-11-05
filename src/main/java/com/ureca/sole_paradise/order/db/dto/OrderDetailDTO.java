package com.ureca.sole_paradise.order.db.dto;

import com.ureca.sole_paradise.product.db.dto.ProductDTO;
import com.ureca.sole_paradise.user.db.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailDTO {

    private ProductDTO productDTO;
    private UserDTO userDTO;
}
