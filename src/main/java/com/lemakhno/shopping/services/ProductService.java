package com.lemakhno.shopping.services;

import java.util.List;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.models.Product;
import com.lemakhno.shopping.models.PurchaseOption;

public interface ProductService {

    public ProductEntity getProductById(String id);

    public void saveProduct(Product product);

    public void addPurchaseOptionToProduct(PurchaseOption purchaseOption, String productId);

    public List<ProductEntity> getProductsByUserEmail(String email);

    public void renameProductById(String id, String newName);

    public void deleteProductById(String id);

    public void clearPurchaseOptionsById(String productId);

    public void deletePurchaseOptionById(String purchaseOptionId);
}
