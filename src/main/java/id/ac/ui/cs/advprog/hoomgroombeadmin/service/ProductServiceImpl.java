package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        UUID productId = new UUID(32, 10);
        product.setId(productId.toString());
        return productRepository.save(product);
    }

    @Override
    public Product edit(Product editedProduct) {
        return null;
    }

    @Override
    public Product getProductById(String productId) {
        if (productRepository.existsById(productId)){
            return productRepository.findById(productId).get();
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public String delete(String productId) {
        if (!productRepository.existsById(productId)){
            return null;
        }
        productRepository.deleteById(productId);
        return productId;
    }
}
