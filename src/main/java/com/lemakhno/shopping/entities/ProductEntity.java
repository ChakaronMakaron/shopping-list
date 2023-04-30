package com.lemakhno.shopping.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_email")
    private String userEmail;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<PurchaseOptionEntity> purchaseOptions;

    public ProductEntity() {}

    public ProductEntity(String name, String userEmail) {
        this.name = name;
        this.userEmail = userEmail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PurchaseOptionEntity> getPurchaseOptions() {
        return purchaseOptions;
    }

    public void setPurchaseOptions(List<PurchaseOptionEntity> purchaseOptions) {
        this.purchaseOptions = purchaseOptions;
    }
    
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void addPurchaseOption(PurchaseOptionEntity purchaseOption) {
        if (purchaseOptions == null) { purchaseOptions = new ArrayList<>(); }
        purchaseOptions.add(purchaseOption);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", purchaseOptions=" + purchaseOptions + ", userEmail="
                + userEmail + "]";
    }
}
