package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/admin/product/create")
    public ResponseEntity<Product> createProductPost(@ModelAttribute Product product, Model model) throws JsonProcessingException {
        Product result = service.create(product);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/admin/product/update/save")
    public ResponseEntity<Product> updateProductPost(@ModelAttribute Product product, Model model) {
        Product result = service.edit(product);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/product/update/{productId}")
    public ResponseEntity<Product> updateProductPage(@PathVariable String productId, Model model){
        Product result = service.getProductById(productId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/admin/product/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId, Model model){
        String result = "Deleted product with ID " + service.delete(productId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/product/list")
    public ResponseEntity<List<Product>> listProduct(Model model){
        List<Product> result = service.getAll();
        model.addAttribute("products", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
