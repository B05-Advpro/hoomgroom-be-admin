package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;

import java.util.List;

public interface PromoCodeService {
    public PromoCode save(PromoCode promoCode);
    public String delete(String promoCodeId);
    public List<PromoCode> getAll();
    public PromoCode getPromoCodeById(String promoCodeId);
}
