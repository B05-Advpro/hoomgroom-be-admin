package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class ProductController {

    @PostMapping("/admin/product/create")
    public String createProductPost(Model model){
        return "Hello, world!";
    }

    @PostMapping("/admin/product/update/save")
    public String updateProductPost(Model model) {
        return "Hello, world!";
    }

    @GetMapping("/admin/product/update/{productId}")
    public String updateProductPage(@PathVariable UUID productId, Model model){
        return "Hello, world!";
    }

    @PostMapping("/admin/product/delete/{productId}")
    public String deleteProduct(@PathVariable UUID productId, Model model){
        return "Hello, world!";
    }

    @GetMapping("/product/list")
    public String listProduct(Model model){
        return "Hello, world!";
    }
}
