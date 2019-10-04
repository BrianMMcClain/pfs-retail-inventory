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

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Product){
            Product b = (Product) o;
            return this.id.equals(b.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}