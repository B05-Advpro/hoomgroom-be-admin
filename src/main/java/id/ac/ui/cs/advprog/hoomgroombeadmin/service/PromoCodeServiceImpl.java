package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService{

    private final PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode save(PromoCode promoCode) {
        try{
            return promoCodeRepository.save(promoCode);
        } catch (Exception e){
            throw new IllegalArgumentException("Already exists a promo code with this name");
        }
    }

    @Override
    public PromoCode getPromoCodeById(String promoCodeId) {
        try {
            Optional<PromoCode> result = promoCodeRepository.findById(promoCodeId);
            if (result.isEmpty()){
                throw new NoSuchElementException("The Id doesn't exist");
            }
            return result.get();
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
