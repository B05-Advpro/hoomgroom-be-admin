package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public String delete(String productId);
    public Product edit(Product editedProduct);
    public List<Product> getAll();
    public Product getProductById(String productId);
}
