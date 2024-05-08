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
        return promoCodeRepository.save(promoCode);
    }

    @Override
    public PromoCode edit(PromoCode editedPromoCode) {
        if (!promoCodeRepository.existsById(editedPromoCode.getCodeId())){
            return null;
        }
        promoCodeRepository.save(editedPromoCode);
        return editedPromoCode;
    }

    @Override
    public PromoCode getPromoCodeById(String promoCodeId) {
        if (!promoCodeRepository.existsById(promoCodeId)){
            return null;
        }
        return promoCodeRepository.findById(promoCodeId).get();
    }

    @Override
    public List<PromoCode> getAll() {
        return promoCodeRepository.findAll();
    }

    @Override
    public String delete(String promoCodeId) {
        if (!promoCodeRepository.existsById(promoCodeId)){
            return null;
        }
        promoCodeRepository.deleteById(promoCodeId);
        return promoCodeId;
    }
}
