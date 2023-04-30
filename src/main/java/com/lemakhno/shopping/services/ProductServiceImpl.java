package com.lemakhno.shopping.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

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
    public ProductEntity getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public void saveProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(null);
        productEntity.setName(product.getName());
        productEntity.setUserEmail(AppUtil.getAuthentication().getName());
        productEntity.setPurchaseOptions(Collections.emptyList());
        productRepository.saveProduct(productEntity);
    }

    @Override
    public void addPurchaseOptionToProduct(PurchaseOption purchaseOption, Integer productId) {
        PurchaseOptionEntity purchaseOptionEntity =
            new PurchaseOptionEntity(purchaseOption.getLink(), purchaseOption.getShopName());
        productRepository.addPurchaseOptionToProduct(purchaseOptionEntity, productId);
    }

    @Override
    public List<ProductEntity> getProductsByUserEmail(String email) {
        return productRepository.getProductsByUserEmail(email);
    }

    @Override
    public void renameProductById(Integer id, String newName) {
        productRepository.renameProductById(id, newName);
    }

    @Override
    public void deleteProductById(Integer id) {
        productRepository.deleteProductById(id);
    }

    @Override
    public void clearPurchaseOptionsById(Integer productId) {
        productRepository.clearPurchaseOptionsById(productId);
    }

    @Override
    public void deleteProductOptionById(Integer productOptionId) {
        productRepository.deleteProductOptionById(productOptionId);
    }
}
