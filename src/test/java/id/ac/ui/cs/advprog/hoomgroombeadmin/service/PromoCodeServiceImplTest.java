package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.PromoCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromoCodeServiceImplTest {
    @Mock
    PromoCodeRepository promoCodeRepository;

    @InjectMocks
    PromoCodeServiceImpl service;

    PromoCode promoCode1;

    @BeforeEach
    void setUp() {
        promoCode1 = new PromoCode();
        promoCode1.setCodeName("BELANJAHEMAT20");
        promoCode1.setEndDate(LocalDate.of(2024, 12, 31));
        promoCode1.setMinimumPayment((double)105000);
        promoCode1.setDescription("Diskon besar");
    }

    @Test
    void testCreateAndFindAll(){
        when(promoCodeRepository.save(any(PromoCode.class))).thenReturn(promoCode1);
        service.create(promoCode1);

        verify(promoCodeRepository, times(1)).save(promoCode1);

        List<PromoCode> promoCodeList = Arrays.asList(promoCode1);
        when(promoCodeRepository.findAll()).thenReturn(promoCodeList);

        List<PromoCode> result = service.getAll();
        assertFalse(result.isEmpty());
        PromoCode savedPromoCode = promoCodeList.getFirst();
        assertNotNull(savedPromoCode);
        assertNotNull(savedPromoCode.getCodeId());
        assertEquals("BELANJAHEMAT20", savedPromoCode.getCodeName());
        assertEquals(20, savedPromoCode.getDiscPercentage());
        assertEquals(LocalDate.of(2024, 12, 31), savedPromoCode.getEndDate());
        assertEquals(105000, savedPromoCode.getMinimumPayment());
        assertEquals("Diskon besar", savedPromoCode.getDescription());
    }
}
