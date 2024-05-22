package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductFilterContext;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService service;

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<Product>> createProductPost(@RequestBody Product product) {
        return CompletableFuture.supplyAsync(() -> {
            Product result = service.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        });
    }

    @PostMapping("/update/save")
    public CompletableFuture<ResponseEntity<Product>> updateProductPost(@RequestBody Product product) {
        return CompletableFuture.supplyAsync(() -> {
            Product result = service.save(product);
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/update/{productId}")
    public CompletableFuture<ResponseEntity<Product>> updateProductPage(@PathVariable String productId) {
        return CompletableFuture.supplyAsync(() -> {
            Product result = service.getProductById(productId);
            return ResponseEntity.ok(result);
        });
    }

    @DeleteMapping("/delete/{productId}")
    public CompletableFuture<ResponseEntity<String>> deleteProduct(@PathVariable String productId) {
        return CompletableFuture.supplyAsync(() -> {
            String result = "Deleted product with ID " + service.delete(productId);
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<Product>>> listProduct() {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> result = service.getAll();
            return ResponseEntity.ok(result);
        });
    }

    @GetMapping("/filter")
    public CompletableFuture<ResponseEntity<List<Product>>> filterProducts(
            @RequestParam String filterType,
            @RequestParam int amount,
            @RequestParam boolean fromLowest,
            @RequestParam(required = false) String keyword) {

        return CompletableFuture.supplyAsync(() -> {
            List<Product> result = new ArrayList<>();
            switch (filterType) {
                case "price":
                    result = service.getProductsByPrice(amount, fromLowest);
                    break;
                case "sales":
                    result = service.getProductsBySales(amount, fromLowest);
                    break;
                case "search":
                    result = service.getProductsBySearched(amount, fromLowest, keyword);
                    break;
                case "tag":
                    result = service.getProductsByTag(amount, fromLowest);
                    break;
            }
            return ResponseEntity.ok(result);
        });
    }
}