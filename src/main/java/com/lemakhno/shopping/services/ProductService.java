package com.lemakhno.shopping.services;

import java.util.List;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.models.Product;
import com.lemakhno.shopping.models.PurchaseOption;

public interface ProductService {

    public ProductEntity getProductById(Integer id);

    public void saveProduct(Product product);

    public void addPurchaseOptionToProduct(PurchaseOption purchaseOption, Integer productId);

    public List<ProductEntity> getProductsByUserEmail(String email);

    public void renameProductById(Integer id, String newName);

    public void deleteProductById(Integer id);

    public void clearPurchaseOptionsById(Integer productId);

    public void deleteProductOptionById(Integer productOptionId);
}
