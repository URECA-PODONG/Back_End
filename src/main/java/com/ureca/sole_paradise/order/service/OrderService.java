package com.ureca.sole_paradise.order.service;

import com.ureca.sole_paradise.order.db.dto.OrderDTO;
import com.ureca.sole_paradise.order.db.dto.OrderDetailDTO;
import com.ureca.sole_paradise.order.db.entity.OrderEntity;
import com.ureca.sole_paradise.order.db.repository.OrderRepository;
import com.ureca.sole_paradise.product.db.dto.ProductDTO;
import com.ureca.sole_paradise.product.db.entity.ProductEntity;
import com.ureca.sole_paradise.user.db.dto.UserDTO;
import com.ureca.sole_paradise.user.db.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public List<OrderDTO> get(int userId) {
        List<OrderEntity> orderEntityList = orderRepository.findByUserEntity_UserId(userId);

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity order : orderEntityList) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .date(order.getCreatedAt().toLocalDate())
                    .productDTO(mapToDTO(order.getProductEntity(), new ProductDTO()))
                    .quantity(order.getQuantity()).build();
            orderDTOList.add(orderDTO);
        }

        return orderDTOList;
    }
    @Transactional
    public OrderDetailDTO getDetail(int orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        return orderEntity.map(entity -> OrderDetailDTO.builder()
                .productDTO(mapToDTO(entity.getProductEntity(), new ProductDTO()))
                .userDTO(mapToDTO(entity.getUserEntity(), new UserDTO()))
                .build()).orElse(null);
    }

    private ProductDTO mapToDTO(final ProductEntity product, final ProductDTO productDTO) {
        productDTO.setProductId(product.getProductId());
        productDTO.setProductTitle(product.getProductTitle());
        productDTO.setProductLink(product.getProductLink());
        productDTO.setProductImage(product.getProductImage());
        productDTO.setProductLprice(product.getProductLprice());
        productDTO.setProductHprice(product.getProductHprice());
        productDTO.setProductMallName(product.getProductMallName());
        productDTO.setProductType(product.getProductType());
        productDTO.setProductBrand(product.getProductBrand());
        productDTO.setProductMaker(product.getProductMaker());
        productDTO.setProductCategory1(product.getProductCategory1());
        productDTO.setProductCategory2(product.getProductCategory2());
        productDTO.setProductCategory3(product.getProductCategory3());
        productDTO.setProductCategory4(product.getProductCategory4());
        return productDTO;
    }

    private UserDTO mapToDTO(final UserEntity user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setAccountEmail(user.getAccountEmail());
        userDTO.setProfileNickname(user.getProfileNickname());
        userDTO.setUserPicture(user.getUserPicture());
        userDTO.setNickname(user.getNickname());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
}
