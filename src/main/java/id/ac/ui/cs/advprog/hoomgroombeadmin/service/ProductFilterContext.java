package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;

import java.util.List;

public class ProductFilterContext {
    private ProductFilterStrategy strategy;

    public ProductFilterContext(ProductFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(ProductFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Product> executeStrategy(List<Product> products) {
        return strategy.sort(products);
    }
}
