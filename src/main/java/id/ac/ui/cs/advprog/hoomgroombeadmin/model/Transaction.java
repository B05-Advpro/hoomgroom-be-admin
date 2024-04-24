package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import id.ac.ui.cs.advprog.hoomgroombeadmin.enums.DeliveryMethod;
import id.ac.ui.cs.advprog.hoomgroombeadmin.enums.DeliveryStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @Transient
    private Map<UUID, Integer> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction() {

    }

    public Transaction(Map<UUID, Integer> products, String promoCodeUsed, UUID pembeli, String deliveryMethod) {
        this.id = UUID.randomUUID();

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }

        this.promoCodeUsed = promoCodeUsed;
        this.pembeli = pembeli;
        this.deliveryStatus = DeliveryStatus.MENUNGGU_VERIFIKASI.getValue();
        this.deliveryCode = "";
        this.setDeliveryMethod(deliveryMethod);
    }

    public void setDeliveryStatus(String status) {
        if (DeliveryStatus.contains(status)) {
            this.deliveryStatus = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setDeliveryMethod(String method) {
        if (DeliveryMethod.contains(method)) {
            this.deliveryMethod = method;
        } else {
            throw new IllegalArgumentException();
        }
    }
}