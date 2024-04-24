package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id;
    private Map<UUID, Integer> products;
    private double totalPrice;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryStatus;
    private String deliveryCode;
    private String deliveryMethod;

    public Transaction(Map<UUID, Integer> products, String promoCodeUsed, UUID pembeli, String deliveryMethod) {

    }

    public void setDeliveryStatus(String status) {

    }
}