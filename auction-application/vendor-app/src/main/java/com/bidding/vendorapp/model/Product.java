package com.bidding.vendorapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    private String name;

    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="vendor_id")
    private Vendor vendor;

    public Product(){}
    public Product(String name, String description, String imageUrl){
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String description, String imageUrl,Category category,Vendor vendor){
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category=category;
        this.vendor=vendor;
    }


}
