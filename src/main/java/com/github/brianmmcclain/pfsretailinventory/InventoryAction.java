package com.github.brianmmcclain.pfsretailinventory;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryAction {

    ObjectMapper objectMapper = new ObjectMapper();

    public String action;

    public String value;

    public String execute(ProductRepository productRepo, TagRepository tagRepo) {
        switch (this.action.toLowerCase()) {
            case "productsbytag":
                return productsByTag(tagRepo, this.value);
            case "tagsbyproduct":
                return tagsByProduct(productRepo, this.value);
            case "inventory":
                return dumpInventory(productRepo);
            case "tags":
                return dumpTags(tagRepo);
        }

        return "Error";
    }

    public String productsByTag(TagRepository tagRepo, String tag) {
        Tag t = tagRepo.findByName(tag);
        return buildJson(t.products);
    }

    public String tagsByProduct(ProductRepository productRepository, String product) {
        Optional<Product> p = productRepository.findById(Long.parseLong(product));
        if (p.isPresent()) {
            return buildJson(p.get().tags);
        } else {
            return "Error";
        }
    }

    public String dumpInventory(ProductRepository productRepository) {
        return buildJson(productRepository.findAll());
    }

    public String dumpTags(TagRepository tagRepo) {
        return buildJson(tagRepo.findAll());
    }

    private String buildJson(Object o) {
        try {
            String retJson = objectMapper.writeValueAsString(o);
            return retJson;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "Error";
        }
    }
}