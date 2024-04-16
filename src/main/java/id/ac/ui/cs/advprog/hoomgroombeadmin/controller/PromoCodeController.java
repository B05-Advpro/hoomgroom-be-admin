package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class PromoCodeController {

    @PostMapping("/admin/promo-code/create")
    public ResponseEntity<String> createPromoCodePost(Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @PostMapping("/admin/promo-code/update/save")
    public ResponseEntity<String> updatePromoCodePost(Model model) {
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/admin/promo-code/update/{promoCodeId}")
    public ResponseEntity<String> updatePromoCodePage(@PathVariable UUID promoCodeId, Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @PostMapping("/admin/promo-code/delete/{promoCodeId}")
    public ResponseEntity<String> deletePromoCode(@PathVariable UUID promoCodeId, Model model){
        return ResponseEntity.ok("Hello, world!");
    }

    @GetMapping("/admin/promo-code/manage")
    public ResponseEntity<String> managePromoCode(Model model){
        return ResponseEntity.ok("Hello, world!");
    }
}
