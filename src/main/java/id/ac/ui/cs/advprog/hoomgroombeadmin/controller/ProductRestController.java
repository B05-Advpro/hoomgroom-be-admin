package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductFilterContext;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/admin/product")
public class ProductRestController {

    @Autowired
    private ProductService service;
    private ProductFilterContext productFilterContext;

    @PostMapping("/create")
    public ResponseEntity<Product> createProductPost(@RequestBody Product product) throws JsonProcessingException {
        Product result = service.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/save")
    public ResponseEntity<Product> updateProductPost(@RequestBody Product product) {
        Product result = service.save(product);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/update/{productId}")
    public ResponseEntity<Product> updateProductPage(@PathVariable String productId){
        Product result = service.getProductById(productId);
        if (result == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId){
        String result = "Deleted product with ID " + service.delete(productId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProduct(){
        List<Product> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/filter?sortby=")
    public ResponseEntity<List<Product>> listProductByTag(){
        List<Product> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam int amount,
            @RequestParam boolean fromLowest,
            @RequestParam(required = false) String keyword) {

        List<Product> result = new ArrayList<>();
        switch (filterType) {
            case "price": result = service.getProductsByPrice(amount, fromLowest); break;
            case "sales": result = service.getProductsBySales(amount, fromLowest); break;
            case "search": result = service.getProductsBySearched(amount, fromLowest, keyword); break;
            case "tag": result = service.getProductsByTag(amount, fromLowest); break;
            default: return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sold")
    @Async
    public CompletableFuture<String> incrementSales(
            @RequestBody HashMap<String, Integer> productsSold){
        return service.incrementSales(productsSold);
    }
}
