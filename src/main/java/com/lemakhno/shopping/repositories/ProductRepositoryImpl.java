package com.lemakhno.shopping.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.lemakhno.shopping.entities.ProductEntity;
import com.lemakhno.shopping.entities.PurchaseOptionEntity;

@Repository
@SuppressWarnings("unchecked")
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ProductRepositoryImpl implements ProductRepository {
    
    private final EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProductEntity> getAllProducts() {
        Query query = entityManager.createNativeQuery("SELECT * FROM product");
        List<ProductEntity> result = query.getResultList();
        return result;
    }

    public ProductEntity getProductById(Integer id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        return product;
    }
    
    public void saveProduct(ProductEntity product) {
        entityManager.persist(product);
    }

    public void addPurchaseOptionToProduct(PurchaseOptionEntity purchaseOption, Integer id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        product.addPurchaseOption(purchaseOption);
    }

    public List<ProductEntity> getProductsByUserEmail(String email) {
        Query query = entityManager.createNativeQuery("SELECT * FROM product WHERE user_email = :email", ProductEntity.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    @Override
    public void renameProductById(Integer id, String newName) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        product.setName(newName);
    }

    @Override
    public void deleteProductById(Integer id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        entityManager.remove(product);
    }

    @Override
    public void clearPurchaseOptionsById(Integer id) {
        Query query = entityManager.createNativeQuery("DELETE FROM purchase_option WHERE product_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteProductOptionById(Integer productOptionId) {
        Query query = entityManager.createNativeQuery("DELETE FROM purchase_option WHERE id = :id");
        query.setParameter("id", productOptionId);
        query.executeUpdate();
    }
}
