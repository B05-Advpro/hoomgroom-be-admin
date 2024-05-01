package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFilterBySearch implements ProductFilterStrategy{

    @Override
    List<Product> sort(List<Product> products, String keyword) {
        return products.stream()
                .filter(product -> product.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
