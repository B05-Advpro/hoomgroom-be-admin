package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.PromoCodeService;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/promo-code")
public class PromoCodeController {

    private final PromoCodeService service;
    private final JwtService jwtService;

    private static final String ROLE = "ADMIN";

    @PostMapping("/create")
    public ResponseEntity<PromoCode> createPromoCodePost(@RequestHeader (value = "Authorization") String token, @RequestBody PromoCode promoCode) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PromoCode result = service.save(promoCode);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/update/save")
    public ResponseEntity<PromoCode> updatePromoCodePost(@RequestHeader (value = "Authorization") String token, @RequestBody PromoCode promoCode) {
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PromoCode result = service.save(promoCode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/update/{promoCodeId}")
    public ResponseEntity<PromoCode> updatePromoCodePage(@RequestHeader (value = "Authorization") String token, @PathVariable String promoCodeId){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        PromoCode result = service.getPromoCodeById(promoCodeId);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{promoCodeId}")
    public ResponseEntity<String> deletePromoCode(@RequestHeader (value = "Authorization") String token, @PathVariable String promoCodeId){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        String result = "Deleted promo code with ID " + service.delete(promoCodeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/manage")
    public ResponseEntity<List<PromoCode>> managePromoCode(@RequestHeader (value = "Authorization") String token){
        token = token.substring(7);
        if (!jwtService.isTokenValid(token) || !jwtService.extractRole(token).equals(ROLE)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<PromoCode> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
