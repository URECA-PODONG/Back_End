package com.ureca.sole_paradise.cart.service;

import com.ureca.sole_paradise.cart.db.dto.CartDTO;
import com.ureca.sole_paradise.cart.db.entity.CartEntity;
import com.ureca.sole_paradise.cart.db.repository.CartRepository;
import com.ureca.sole_paradise.product.db.repository.ProductRepository;
import com.ureca.sole_paradise.user.db.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CartDTO> getCartItemsByUserId(Integer userId) {
        List<CartEntity> cartEntities = cartRepository.findByUser_UserId(userId);
        if (cartEntities == null || cartEntities.isEmpty()) {
            return new ArrayList<>(); // 빈 리스트 반환
        }
        return cartEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    public void addToCart(CartDTO cartDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUser(userRepository.findById(cartDTO.getUserId()).orElseThrow()); // 여기서 setUser를 사용합니다.
        cartEntity.setProduct(productRepository.findById(cartDTO.getProductId()).orElseThrow());
        cartEntity.setQuantity(cartDTO.getQuantity());
        cartRepository.save(cartEntity);
    }

    public void updateCartQuantity(Integer cartId, Integer quantity) {
        CartEntity cartEntity = cartRepository.findById(cartId).orElseThrow();
        cartEntity.setQuantity(quantity);
        cartRepository.save(cartEntity);
    }

    public void deleteCartItem(Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    private CartDTO convertToDTO(CartEntity cartEntity) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cartEntity.getCartId());
        dto.setUserId(cartEntity.getUser().getUserId());
        dto.setProductId(cartEntity.getProduct().getProductId());
        dto.setQuantity(cartEntity.getQuantity());
        dto.setProductImage(cartEntity.getProduct().getProductImage());
        dto.setProductTitle(cartEntity.getProduct().getProductTitle());
        dto.setProductLprice(cartEntity.getProduct().getProductLprice());
        return dto;
    }
}
