package com.bidding.vendorapp.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private int categoryId;

    private String name;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL)
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name="vendor_id")
    private Vendor vendor;

    public Category(String name){
        this.name = name;
    }

    public Category(){}


    public Category(String name, Vendor v) {
        this.name = name;
        vendor=v;
    }
}
