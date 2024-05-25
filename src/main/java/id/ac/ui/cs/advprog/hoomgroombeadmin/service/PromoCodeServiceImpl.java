package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class
PromoCodeServiceImpl implements PromoCodeService{

    @Autowired
    PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode save(PromoCode promoCode) {
        return promoCodeRepository.save(promoCode);
    }

    @Override
    public PromoCode getPromoCodeById(String promoCodeId) {
        try {
            return promoCodeRepository.findById(promoCodeId).get();
        } catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public List<PromoCode> getAll() {
        return promoCodeRepository.findAll();
    }

    @Override
    public String delete(String promoCodeId) {
        promoCodeRepository.deleteById(promoCodeId);
        return promoCodeId;
    }
}
