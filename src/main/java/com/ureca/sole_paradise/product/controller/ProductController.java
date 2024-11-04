package com.ureca.sole_paradise.product.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ureca.sole_paradise.product.db.dto.ProductDTO;
import com.ureca.sole_paradise.product.service.ProductService;
import com.ureca.sole_paradise.util.ReferencedException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable(name = "productId") final Integer productId) {
        return ResponseEntity.ok(productService.get(productId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid final ProductDTO productDTO) {
        final Integer createdProductId = productService.create(productDTO);
        return new ResponseEntity<>(createdProductId, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Integer> updateProduct(
            @PathVariable(name = "productId") final Integer productId,
            @RequestBody @Valid final ProductDTO productDTO) {
        productService.update(productId, productDTO);
        return ResponseEntity.ok(productId);
    }

    @DeleteMapping("/{productId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "productId") final Integer productId) {
        final ReferencedWarning referencedWarning = productService.getReferencedWarning(productId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    // 네이버 API JSON 데이터 저장용 엔드포인트
    @PostMapping("/saveProducts")
    public ResponseEntity<Void> saveProducts(@RequestBody String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);

            for (JsonNode item : root.get("items")) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductTitle(item.get("title").asText());
                productDTO.setProductLink(item.get("link").asText());
                productDTO.setProductImage(item.get("image").asText());
                productDTO.setProductLprice(item.get("lprice").asInt());
                productDTO.setProductHprice(item.has("hprice") && !item.get("hprice").asText().isEmpty()
                        ? item.get("hprice").asInt() : null);
                productDTO.setProductMallName(item.get("mallName").asText());
                productDTO.setProductType(item.get("productType").asInt());
                productDTO.setProductBrand(item.has("brand") ? item.get("brand").asText() : null);
                productDTO.setProductMaker(item.has("maker") ? item.get("maker").asText() : null);
                productDTO.setProductCategory1(item.get("category1").asText());
                productDTO.setProductCategory2(item.get("category2").asText());
                productDTO.setProductCategory3(item.get("category3").asText());
                productDTO.setProductCategory4(item.get("category4").asText());
                productDTO.setCreatedAt(OffsetDateTime.now());

                productService.create(productDTO);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
