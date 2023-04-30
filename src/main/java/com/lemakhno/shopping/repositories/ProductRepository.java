package com.lemakhno.shopping.repositories;

import java.util.List;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.entities.PurchaseOptionEntity;

public interface ProductRepository {
    
    public List<ProductEntity> getAllProducts();

    public ProductEntity getProductById(Integer id);

    public void saveProduct(ProductEntity product);

    public void addPurchaseOptionToProduct(PurchaseOptionEntity purchaseOption, Integer id);

    public List<ProductEntity> getProductsByUserEmail(String email);

    public void renameProductById(Integer id, String newName);

    public void deleteProductById(Integer id);

    public void clearPurchaseOptionsById(Integer id);

    public void deleteProductOptionById(Integer productOptionId);
}