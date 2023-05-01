package com.lemakhno.shopping.services;

import java.util.Collections;
import java.util.List;

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
    public ProductEntity getProductById(Integer id) {
        ProductEntity product = productRepository.getProductById(id);
        checkCrossUserResourceRequest(product.getUserEmail());
        return product;
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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addPurchaseOptionToProduct(PurchaseOption purchaseOption, Integer productId) {
        ProductEntity product = productRepository.getProductByIdForUpdate(productId);
        checkCrossUserResourceRequest(product.getUserEmail());
        PurchaseOptionEntity purchaseOptionEntity =
            new PurchaseOptionEntity(purchaseOption.getLink(), purchaseOption.getShopName());
        product.addPurchaseOption(purchaseOptionEntity);
    }

    @Override
    public List<ProductEntity> getProductsByUserEmail(String email) {
        return productRepository.getProductsByUserEmail(email);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void renameProductById(Integer id, String newName) {
        ProductEntity product = productRepository.getProductByIdForUpdate(id);
        checkCrossUserResourceRequest(product.getUserEmail());
        product.setName(newName);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteProductById(Integer id) {
        ProductEntity product = productRepository.getProductByIdForUpdate(id);
        checkCrossUserResourceRequest(product.getUserEmail());
        productRepository.remove(product);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void clearPurchaseOptionsById(Integer productId) {
        ProductEntity product = productRepository.getProductByIdForUpdate(productId);
        checkCrossUserResourceRequest(product.getUserEmail());
        productRepository.clearPurchaseOptionsByProductId(productId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deletePurchaseOptionById(Integer purchaseOptionId) {
        PurchaseOptionEntity purchaseOption = productRepository.getPurchaseOptionById(purchaseOptionId);
        checkCrossUserResourceRequest(purchaseOption.getProduct().getUserEmail());
        productRepository.remove(purchaseOption);
    }

    private void checkCrossUserResourceRequest(String resourceHolderEmail) {
        String authEmail = AppUtil.getAuthentication().getName();
        if (!authEmail.equals(resourceHolderEmail)) {
            throw new IllegalStateException("Attempted to acess another user's resource");
        }
    }
}
