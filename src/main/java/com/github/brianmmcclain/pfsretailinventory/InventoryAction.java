package com.github.brianmmcclain.pfsretailinventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
            case "recommendations":
                return getRecommendations(productRepo, tagRepo, this.value);
            case "checkout":
                return checkout(productRepo, this.value);
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

    public String getRecommendations(ProductRepository productRepository, TagRepository tagRepository, String value) {
        String[] prodIds = value.split(",");
        Map<Tag, Integer> tagWeight = new HashMap<Tag, Integer>();

        // Get a count of all tags
        for (int i = 0; i < prodIds.length; i++) {
            Optional<Product> p = productRepository.findById(Long.parseLong(prodIds[i]));
            if (p.isPresent()) {
                List<Tag> tags = p.get().tags;
                for (int j = 0; j < tags.size(); j++) {
                    Tag tag = tags.get(j);
                    int count = tagWeight.containsKey(tag) ? tagWeight.get(tag) : 0;
                    tagWeight.put(tag, count + 1);
                }
            }
        }

        // Get all products not in prodIds that share tags
        Iterator iterator = tagWeight.keySet().iterator();
        List<Product> recommendations = new ArrayList<>();
        while (iterator.hasNext()) {
            Tag t = (Tag) iterator.next();
            List<Product> prods = t.products;
            Iterator prodIterator = prods.iterator();
            while (prodIterator.hasNext()) {
                Product p = (Product) prodIterator.next();
                if (!Arrays.stream(prodIds).anyMatch(p.id.toString()::equals) && !recommendations.contains(p)) {
                    recommendations.add(p);
                }
            }
        }

        return buildJson(recommendations.toArray());
    }

    private String checkout(ProductRepository productRepository, String value) {
        List<Product> products = new ArrayList<>();
        String[] productIds = value.split(",");

        for (int i = 0; i < productIds.length; i++) {
            Optional<Product> p = productRepository.findById(Long.parseLong(productIds[i]));
            if (p.isPresent()) {
                products.add(p.get());
            }
        }

        return "{\"status\": \"done\"}";
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