package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.PromoCodeService;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/promo-code")
public class PromoCodeController {

    @Autowired
    private PromoCodeService service;

    @Autowired
    private JwtService jwtService;

    private static final String ROLE = "ADMIN";

    @PostMapping("/create")
    public ResponseEntity<PromoCode> createPromoCodePost(@RequestHeader (value = "Authorization") String token, @RequestBody PromoCode promoCode) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        PromoCode result = service.save(promoCode);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/save")
    public ResponseEntity<PromoCode> updatePromoCodePost(@RequestHeader (value = "Authorization") String token, @RequestBody PromoCode promoCode) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        PromoCode result = service.save(promoCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/update/{promoCodeId}")
    public ResponseEntity<PromoCode> updatePromoCodePage(@RequestHeader (value = "Authorization") String token, @PathVariable String promoCodeId){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        PromoCode result = service.getPromoCodeById(promoCodeId);
        if (result == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{promoCodeId}")
    public ResponseEntity<String> deletePromoCode(@RequestHeader (value = "Authorization") String token, @PathVariable String promoCodeId){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        String result = "Deleted promo code with ID " + service.delete(promoCodeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/manage")
    public ResponseEntity<List<PromoCode>> managePromoCode(@RequestHeader (value = "Authorization") String token){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        List<PromoCode> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
