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

    private final ProductFilterContext filterContext = new ProductFilterContext(null);

    @Override
    public Product create(Product product) {
        UUID productId = new UUID(32, 10);
        product.setId(productId.toString());
        return productRepository.save(product);
    }

    @Override
    public Product edit(Product editedProduct) {
        if (!productRepository.existsById(editedProduct.getId())){
            return null;
        }
        productRepository.save(editedProduct);
        return editedProduct;
    }

    @Override
    public Product getProductById(String productId) {
        if (!productRepository.existsById(productId)){
            return null;
        }
        return productRepository.findById(productId).get();
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

    /* Will return empty list if there are no products found
    amount: the number of products that will be returned in the list
    fromLowest: true when you want to sort price from lowest
    */
    public List<Product> getProductsByPrice(int amount, boolean fromLowest){
        filterContext.setStrategy(new ProductFilterByPrice());
        List<Product> sortedbyPrice = filterContext.executeStrategy(productRepository.findAll());

        if (amount > sortedbyPrice.size()){
            if (fromLowest) return sortedbyPrice;
            else return sortedbyPrice.reversed();
        }
        if (fromLowest) {
            return sortedbyPrice.subList(0, amount);
        } else {
            return sortedbyPrice.reversed().subList(0, amount);
        }
    }

}
