package com.ureca.sole_paradise.order.controller;

import com.ureca.sole_paradise.order.db.dto.OrderDTO;
import com.ureca.sole_paradise.order.db.dto.OrderDetailDTO;
import com.ureca.sole_paradise.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<OrderDTO>> getList(
            @PathVariable(name = "userId") final int userId) {
        return ResponseEntity.ok(orderService.get(userId));
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<OrderDetailDTO> getDetail(
            @PathVariable(name = "orderId") final int orderId) {
        return ResponseEntity.ok(orderService.getDetail(orderId));
    }

}
