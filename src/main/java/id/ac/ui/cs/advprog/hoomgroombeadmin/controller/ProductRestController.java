package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductService;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin/product")
public class ProductRestController {

    private final ProductService service;
    private final JwtService jwtService;

    private static final String ROLE = "ADMIN";

    @PostMapping("/create")
    public ResponseEntity<Product> createProductPost(@RequestHeader (value = "Authorization") String token, @RequestBody Product product){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Product result = service.save(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/save")
    public ResponseEntity<Product> updateProductPost(@RequestHeader (value = "Authorization") String token, @RequestBody Product product) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Product result = service.save(product);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/update/{productId}")
    public ResponseEntity<Product> updateProductPage(@PathVariable String productId){
        Product result = service.getProductById(productId);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@RequestHeader (value = "Authorization") String token, @PathVariable String productId) throws ExecutionException, InterruptedException {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String result = "Deleted product with ID " + service.delete(productId).get();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProduct() throws ExecutionException, InterruptedException{
        CompletableFuture<List<Product>> result = service.getAll();
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam int amount,
            @RequestParam boolean fromLowest,
            @RequestParam(required = false) String keyword) throws ExecutionException, InterruptedException {

        List<Product> result = new ArrayList<>();
        switch (filterType) {
            case "price": result = service.getProductsByPrice(amount, fromLowest).get(); break;
            case "sales": result = service.getProductsBySales(amount, fromLowest).get(); break;
            case "search": result = service.getProductsBySearched(amount, fromLowest, keyword).get(); break;
            case "tag": result = service.getProductsByTag(amount, fromLowest).get(); break;
            default: return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sold")
    @Async
    public CompletableFuture<String> incrementSales(
            @RequestHeader (value = "Authorization") String token,
            @RequestBody Map<String, Integer> productsSold){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token)){
            return CompletableFuture.failedFuture(new IllegalAccessException("Log in needed"));
        }
        return service.incrementSales(productsSold);
    }
}
