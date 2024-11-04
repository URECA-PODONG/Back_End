package com.ureca.sole_paradise.product.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ureca.sole_paradise.product.db.entity.ProductEntity;
import com.ureca.sole_paradise.product.db.dto.ProductDTO;
import com.ureca.sole_paradise.product.db.repository.ProductRepository;
import com.ureca.sole_paradise.util.NotFoundException;
import com.ureca.sole_paradise.util.ReferencedWarning;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll(Sort.by("productId"))
                .stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Integer productId) {
        return productRepository.findById(productId)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductDTO productDTO) {
        ProductEntity product = new ProductEntity();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getProductId();
    }

    public void update(final Integer productId, final ProductDTO productDTO) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Integer productId) {
        productRepository.deleteById(productId);
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

    private ProductEntity mapToEntity(final ProductDTO productDTO, final ProductEntity product) {
        product.setProductTitle(productDTO.getProductTitle());
        product.setProductLink(productDTO.getProductLink());
        product.setProductImage(productDTO.getProductImage());
        product.setProductLprice(productDTO.getProductLprice());
        product.setProductHprice(productDTO.getProductHprice());
        product.setProductMallName(productDTO.getProductMallName());
        product.setProductType(productDTO.getProductType());
        product.setProductBrand(productDTO.getProductBrand());
        product.setProductMaker(productDTO.getProductMaker());
        product.setProductCategory1(productDTO.getProductCategory1());
        product.setProductCategory2(productDTO.getProductCategory2());
        product.setProductCategory3(productDTO.getProductCategory3());
        product.setProductCategory4(productDTO.getProductCategory4());
        return product;
    }

    public ReferencedWarning getReferencedWarning(final Integer productId) {
        final ProductEntity product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        return null;
    }
}