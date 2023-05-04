package com.lemakhno.shopping.repositories;

import java.util.List;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.entities.PurchaseOptionEntity;

public interface ProductRepository {
    
    public List<ProductEntity> getAllProducts();

    public ProductEntity getProductById(String id);

    public ProductEntity getProductByIdForUpdate(String id);

    public PurchaseOptionEntity getPurchaseOptionById(String id);

    public void remove(Object entity);

    public void saveProduct(ProductEntity product);

    public void addPurchaseOptionToProduct(PurchaseOptionEntity purchaseOption, String id);

    public List<ProductEntity> getProductsByUserEmail(String email);

    public void renameProductById(String id, String newName);

    public void deleteProductById(String id);

    public void clearPurchaseOptionsByProductId(String id);

    public void deletePurchaseOptionById(String purchaseOptionId);
}
