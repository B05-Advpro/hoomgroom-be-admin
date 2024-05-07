package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;

import java.util.List;

public interface PromoCodeService {
    public PromoCode create(PromoCode promoCode);
    public String delete(String promoCodeId);
    public PromoCode edit(PromoCode editedPromoCode);
    public List<PromoCode> getAll();
    public PromoCode getPromoCodeById(String promoCodeId);
}
