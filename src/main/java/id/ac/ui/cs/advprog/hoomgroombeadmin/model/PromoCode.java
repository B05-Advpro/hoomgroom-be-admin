package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class PromoCode {
    String codeId;
    String codeName;
    LocalDate endDate;
    @Setter
    String description;
    Double minimumPayment;
    int discPercentage = 0;

    private static Set<String> allNames = new HashSet<>();

    public PromoCode(){};

    public PromoCode(String codeName, LocalDate endDate, String description, Double minimumPayment){
        if (!isAlphaNumeric(codeName) || !isNameUnique(codeName)){
            throw new IllegalArgumentException("Nama kode promo tidak valid.");
        }
        if (!isValidEndDate(endDate)){
            throw new IllegalArgumentException("Tanggal kode promo berakhir tidak bisa sebelum tanggal saat ini.");
        }
        if (minimumPayment < 1){
            throw new IllegalArgumentException("Minimal transaksi harus lebih dari 0!");
        }

        this.codeId = new UUID(32, 10).toString();
        this.codeName = codeName.toUpperCase();
        this.endDate = endDate;
        this.description = description;
        this.minimumPayment = minimumPayment;

        allNames.add(this.codeName);
    }

    public boolean isValidEndDate(LocalDate endDate){
        return endDate.isAfter(LocalDate.now());
    }

    public boolean isAlphaNumeric(String codeName){
        if (Character.isDigit(codeName.charAt(0))){
            return false;
        }
        for (int i=0; i < codeName.length(); i++){
            if (Character.isDigit(codeName.charAt(i))){
                try{
                    String disc = codeName.substring(i);
                    this.discPercentage = Integer.parseInt(disc);
                } catch (NumberFormatException e){
                    return false;
                }
                break;
            }
        }
        return this.discPercentage <= 100 && this.discPercentage >= 1;
    }

    public boolean isNameUnique(String codeName){
        return !allNames.contains(codeName);
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Tanggal kode promo berakhir tidak bisa sebelum tanggal saat ini.");
        }
        this.endDate = endDate;
    }

    public void setMinimumPayment(Double minimumPayment){
        if (minimumPayment < 1){
            throw new IllegalArgumentException("Minimal transaksi harus lebih dari 0!");
        }
        this.minimumPayment = minimumPayment;
    }

    public void setCodeName(String name){
        if (!isAlphaNumeric(name) || !isNameUnique(name)){
            throw new IllegalArgumentException("Nama kode promo tidak valid.");
        }
        allNames.remove(this.codeName);
        this.codeName = name.toUpperCase();
        allNames.add(this.codeName);
    }

    public void setCodeId(String id) throws IllegalArgumentException{
        UUID uuid = UUID.fromString(id);
        this.codeId = id;
    }
}
