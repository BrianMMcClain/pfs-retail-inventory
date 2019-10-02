package com.github.brianmmcclain.pfsretailinventory;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {

    @Id
    public Long id;

    public String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    List<Product> products;

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.length();
    }
}