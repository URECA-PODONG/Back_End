package com.ureca.sole_paradise.cart.controller;

import com.ureca.sole_paradise.cart.service.CartService;
import com.ureca.sole_paradise.cart.db.dto.CartDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserCart(@PathVariable("userId") Integer userId) {
        try {
            List<CartDTO> cartItems = cartService.getCartItemsByUserId(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("장바구니 항목을 불러오는 중 오류가 발생했습니다.");
        }
    }


    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        cartService.addToCart(cartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 담겼습니다");
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<String> updateCartQuantity(@PathVariable("cartId") Integer cartId, @RequestParam("quantity") Integer quantity) {
        cartService.updateCartQuantity(cartId, quantity);
        return ResponseEntity.ok("수량변경되었습니다");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("cartId") Integer cartId) {
        cartService.deleteCartItem(cartId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
