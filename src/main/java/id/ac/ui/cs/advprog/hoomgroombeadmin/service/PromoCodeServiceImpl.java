package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PromoCodeServiceImpl implements PromoCodeService{

    @Autowired
    PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode create(PromoCode promoCode) {
        return null;
    }

    @Override
    public PromoCode edit(PromoCode editedPromoCode) {
        return null;
    }

    @Override
    public PromoCode getPromoCodeById(String promoCodeId) {
        return null;
    }

    @Override
    public List<PromoCode> getAll() {
        return null;
    }

    @Override
    public String delete(String promoCodeId) {
        return null;
    }
}
