package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "promoCode")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String codeId;

    @Column(unique=true)
    String codeName;

    @Setter
    String description;

    LocalDate endDate;
    Double minimumPayment;
    int discPercentage = 0;

    public PromoCode(String codeName, LocalDate endDate, String description, Double minimumPayment){
        if (!isAlphaNumeric(codeName)){
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
        if (!isAlphaNumeric(name)){
            throw new IllegalArgumentException("Nama kode promo tidak valid.");
        }
        this.codeName = name.toUpperCase();
    }

    public void setCodeId(String id) throws IllegalArgumentException{
        UUID.fromString(id);
        this.codeId = id;
    }
}
