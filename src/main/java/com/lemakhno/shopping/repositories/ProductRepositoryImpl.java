package com.lemakhno.shopping.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
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

    @Override
    public void remove(Object entity) {
        entityManager.remove(entity);
    }

    @Override
    public PurchaseOptionEntity getPurchaseOptionById(Integer id) {
        return entityManager.find(PurchaseOptionEntity.class, id);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        Query query = entityManager.createNativeQuery("SELECT * FROM product");
        List<ProductEntity> result = query.getResultList();
        return result;
    }

    @Override
    public ProductEntity getProductById(Integer id) {
        return entityManager.find(ProductEntity.class, id);
    }

    @Override
    public ProductEntity getProductByIdForUpdate(Integer id) {
        return entityManager.find(ProductEntity.class, id, LockModeType.PESSIMISTIC_WRITE);
    }
    
    @Override
    public void saveProduct(ProductEntity product) {
        entityManager.persist(product);
    }

    @Override
    public void addPurchaseOptionToProduct(PurchaseOptionEntity purchaseOption, Integer id) {
        ProductEntity product = entityManager.find(ProductEntity.class, id);
        product.addPurchaseOption(purchaseOption);
    }

    @Override
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
    public void clearPurchaseOptionsByProductId(Integer id) {
        Query query = entityManager.createNativeQuery("DELETE FROM purchase_option WHERE product_id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deletePurchaseOptionById(Integer purchaseOptionId) {
        Query query = entityManager.createNativeQuery("DELETE FROM purchase_option WHERE id = :id");
        query.setParameter("id", purchaseOptionId);
        query.executeUpdate();
    }
}
