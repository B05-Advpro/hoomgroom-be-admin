package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Transaction;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

public class TransactionBuilder {
    private Map<UUID, Integer> products;
    private String promoCodeUsed;
    private UUID pembeli;
    private String deliveryMethod;

    public TransactionBuilder setProducts(Map<UUID, Integer> products) {
        this.products = products;
    }

    public TransactionBuilder setPromoCodeUsed(String promoCodeUsed) {
        this.promoCodeUsed = promoCodeUsed;
    }

    public TransactionBuilder setPembeli(UUID pembeli) {
        this.pembeli = pembeli;
    }

    public TransactionBuilder setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Transaction build() {
        return new Transaction(products, promoCodeUsed, pembeli, deliveryMethod);
    }
}
