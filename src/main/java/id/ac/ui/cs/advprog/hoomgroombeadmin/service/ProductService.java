package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    public Product save(Product product);
    public CompletableFuture<String> delete(String productId);
    public CompletableFuture<List<Product>> getAll();
    public Product getProductById(String productId);
    public CompletableFuture<List<Product>> getProductsByPrice(int amount, boolean fromLowest);
    public CompletableFuture<List<Product>> getProductsBySales(int amount, boolean fromLowest);
    public CompletableFuture<List<Product>> getProductsBySearched(int amount, boolean fromLowest, String keyword);
    public CompletableFuture<List<Product>> getProductsByTag(int amount, boolean fromLowest);
    public CompletableFuture<String> incrementSales(Map<String, Integer> productsSold);
}
