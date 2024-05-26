package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    private final ProductFilterContext filterContext = new ProductFilterContext(null);

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(String productId) {
        try {
            return productRepository.findById(productId).get();
        } catch (NoSuchElementException e){
            return null;
        }
    }
    @Override
    public CompletableFuture<List<Product>> getAll() {
        return CompletableFuture.supplyAsync(() -> productRepository.findAll());
    }

    @Override
    public String delete(String productId) {
        productRepository.deleteById(productId);
        return productId;
    }

    /* Will return empty list if there are no products found
    amount: the number of products that will be returned in the list
    fromLowest: true when you want to sort price from lowest
    */
    @Override
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

    @Override
    public List<Product> getProductsBySearched(int amount, boolean fromLowest, String keyword){
        filterContext.setStrategy(new ProductFilterBySearch());
        List<Product> searchedProducts = filterContext.executeStrategy(productRepository.findByProductNameContainingIgnoreCase(keyword));

        if (amount > searchedProducts.size()){
            if (fromLowest) return searchedProducts;
            else return searchedProducts.reversed();
        }
        if (fromLowest) {
            return searchedProducts.subList(0, amount);
        } else {
            return searchedProducts.reversed().subList(0, amount);
        }
    }

    /* Will return empty list if there are no products found
    amount: the number of products that will be returned in the list
    fromLowest: true when you want to sort price from lowest
    */
    @Override
    public List<Product> getProductsBySales(int amount, boolean fromLowest){
        filterContext.setStrategy(new ProductFilterBySales());
        List<Product> sortedBySales = filterContext.executeStrategy(productRepository.findAll());

        if (amount > sortedBySales.size()){
            if (fromLowest) return sortedBySales;
            else return sortedBySales.reversed();
        }
        if (fromLowest) {
            return sortedBySales.subList(0, amount);
        } else {
            return sortedBySales.reversed().subList(0, amount);
        }
    }

    @Override
    public List<Product> getProductsByTag(int amount, boolean fromLowest){
        filterContext.setStrategy(new ProductFilterByTag());
        List<Product> taggedProducts = filterContext.executeStrategy(productRepository.findAll());

        if (amount > taggedProducts.size()){
            if (fromLowest) return taggedProducts;
            else return taggedProducts.reversed();
        }
        if (fromLowest) {
            return taggedProducts.subList(0, amount);
        } else {
            return taggedProducts.reversed().subList(0, amount);
        }
    }

    @Async
    @Transactional
    @Override
    public CompletableFuture<String> incrementSales(Map<String, Integer> productsSold) throws IllegalArgumentException{
        StringBuilder allResult = new StringBuilder();
        boolean error = false;
        String productId;

        for (Map.Entry<String, Integer> entry: productsSold.entrySet()){
            try{
                productId = entry.getKey();
                int result = productRepository.incrementSales(productId, productsSold.get(productId));
                if (result == 0){
                    throw new IllegalArgumentException();
                }
                allResult.append("Sales successfully incremented for product ID: ");
                allResult.append(productId);
                allResult.append("\n");

            } catch (Exception e) {
                productId = entry.getKey();
                allResult.append("Error incrementing sales for product ID: ");
                allResult.append(productId);
                allResult.append("\n");
                error = true;
            }
        }
        if (error){
            return CompletableFuture.failedFuture(new IllegalArgumentException(allResult.toString()));
        }
        return CompletableFuture.completedFuture(allResult.toString());
    }
}
