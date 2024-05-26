package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;

import java.util.Comparator;
import java.util.List;

public class ProductFilterByTag implements ProductFilterStrategy{
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(p -> p.getTag().stream().findFirst().orElse("")))
                .toList();
    }
}
