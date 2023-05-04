package com.lemakhno.shopping.services;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.entities.PurchaseOptionEntity;
import com.lemakhno.shopping.models.Product;
import com.lemakhno.shopping.models.PurchaseOption;
import com.lemakhno.shopping.repositories.ProductRepository;
import com.lemakhno.shopping.util.AppUtil;

@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity getProductById(String id) {
        ProductEntity product = productRepository.getProductById(id);
        return product;
    }

    @Override
    public void saveProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(generateId());
        productEntity.setName(product.getName());
        productEntity.setUserEmail(AppUtil.getAuthentication().getName());
        productEntity.setPurchaseOptions(Collections.emptyList());
        productRepository.saveProduct(productEntity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addPurchaseOptionToProduct(PurchaseOption purchaseOption, String productId) {
        ProductEntity product = productRepository.getProductByIdForUpdate(productId);
        PurchaseOptionEntity purchaseOptionEntity =
            new PurchaseOptionEntity(purchaseOption.getLink(), purchaseOption.getShopName(), generateId());
        product.addPurchaseOption(purchaseOptionEntity);
    }

    @Override
    public List<ProductEntity> getProductsByUserEmail(String email) {
        return productRepository.getProductsByUserEmail(email);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void renameProductById(String id, String newName) {
        ProductEntity product = productRepository.getProductByIdForUpdate(id);
        product.setName(newName);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteProductById(String id) {
        ProductEntity product = productRepository.getProductByIdForUpdate(id);
        productRepository.remove(product);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void clearPurchaseOptionsById(String productId) {
        productRepository.clearPurchaseOptionsByProductId(productId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePurchaseOptionById(String purchaseOptionId) {
        PurchaseOptionEntity purchaseOption = productRepository.getPurchaseOptionById(purchaseOptionId);
        productRepository.remove(purchaseOption);
    }

    private String generateId() {
        return RandomStringUtils.randomAlphanumeric(15);
    }
}
