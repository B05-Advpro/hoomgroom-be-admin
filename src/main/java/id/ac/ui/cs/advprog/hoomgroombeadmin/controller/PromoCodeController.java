package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.PromoCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/promo-code")
public class PromoCodeController {

    private PromoCodeService service;

    @PostMapping("/create")
    public ResponseEntity<PromoCode> createPromoCodePost(@RequestBody PromoCode promoCode) {
        PromoCode result = service.create(promoCode);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/save")
    public ResponseEntity<PromoCode> updatePromoCodePost(@RequestBody PromoCode promoCode) {
        PromoCode result = service.edit(promoCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/update/{promoCodeId}")
    public ResponseEntity<PromoCode> updatePromoCodePage(@PathVariable String promoCodeId){
        PromoCode result = service.getPromoCodeById(promoCodeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{promoCodeId}")
    public ResponseEntity<String> deletePromoCode(@PathVariable String promoCodeId){
        String result = "Deleted promo code with ID " + service.delete(promoCodeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/manage")
    public ResponseEntity<List<PromoCode>> managePromoCode(){
        List<PromoCode> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
