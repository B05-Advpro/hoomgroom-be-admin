package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    public Product create(Product product);
    public String delete(String productId);
    public Product edit(Product editedProduct);
    public List<Product> getAll();
    public Product getProductById(String productId);
    public List<Product> getProductsByPrice(int amount, boolean fromLowest);
    public List<Product> getProductsBySales(int amount, boolean fromLowest);
    public List<Product> getProductsBySearched(int amount, boolean fromLowest, String keyword);
    public List<Product> getProductsByTag(int amount, boolean fromLowest);
    public CompletableFuture<Integer> incrementSales(String productId, int quantity);
}
