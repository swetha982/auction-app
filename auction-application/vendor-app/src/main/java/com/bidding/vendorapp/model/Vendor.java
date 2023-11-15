package com.bidding.vendorapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private int vendorId;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    public Vendor(){}
    public Vendor(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
