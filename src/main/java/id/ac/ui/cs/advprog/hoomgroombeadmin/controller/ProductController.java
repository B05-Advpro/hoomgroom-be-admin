package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ProductController {

    @PostMapping("/admin/product/create")
    public ResponseEntity<String> createProductPost(Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @PostMapping("/admin/product/update/save")
    public ResponseEntity<String> updateProductPost(Model model) {
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/admin/product/update/{productId}")
    public ResponseEntity<String> updateProductPage(@PathVariable UUID productId, Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @PostMapping("/admin/product/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID productId, Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/product/list")
    public ResponseEntity<String> listProduct(Model model){
        return ResponseEntity.ok("Hello, world!");
    }
}
