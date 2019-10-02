package com.github.brianmmcclain.pfsretailinventory;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Product {

    @Id
    public Long id;

    public String name;

    public float price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_tag")
    public List<Tag> tags;

    public String toString() {
        return this.name;
    }
}