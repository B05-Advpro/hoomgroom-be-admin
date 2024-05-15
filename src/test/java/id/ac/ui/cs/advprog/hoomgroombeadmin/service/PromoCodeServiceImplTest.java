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
        promoCode1.setMinimumPayment((double)105000);
        promoCode1.setDescription("Diskon besar");
    }

    @Test
    void testCreateAndFindAll(){
        promoCode1.setCodeName("BELANJAHEMAT75");
        promoCode1.setCodeId("6f42392e-40a2-475a-9c00-c667307c20d8");
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
        assertEquals(105000, savedPromoCode.getMinimumPayment());
        assertEquals("Diskon besar", savedPromoCode.getDescription());
    }

    @Test
    void testDelete(){
        String promoCodeId = (new UUID(32, 10)).toString();
        when(promoCodeRepository.existsById(promoCodeId)).thenReturn(true);

        String result = service.delete(promoCodeId);
        verify(promoCodeRepository, times(1)).existsById(promoCodeId);
        verify(promoCodeRepository, times(1)).deleteById(promoCodeId);
        assertEquals(promoCodeId, result);
    }

    @Test
    void testDeleteIfIdNotFound(){
        String promoCodeId = (new UUID(32, 10)).toString();
        when(promoCodeRepository.existsById(promoCodeId)).thenReturn(false);

        String result = service.delete(promoCodeId);
        verify(promoCodeRepository, times(1)).existsById(promoCodeId);
        assertNull(result);
    }

    @Test
    void testGetProductByIdFound(){
        promoCode1.setCodeName("BELANJAHEMAT60");
        promoCode1.setCodeId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        when(promoCodeRepository.existsById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(true);
        when(promoCodeRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.ofNullable(promoCode1));

        PromoCode savedPromoCode = service.getPromoCodeById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(promoCodeRepository, times(1)).existsById(any(String.class));
        verify(promoCodeRepository, times(1)).findById(any(String.class));
        assertNotNull(savedPromoCode);
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", savedPromoCode.getCodeId());
    }

    @Test
    void testGetProductByIdNotFound(){
        when(promoCodeRepository.existsById("0000")).thenReturn(false);

        assertNull(service.getPromoCodeById("0000"));
        verify(promoCodeRepository, times(1)).existsById("0000");
    }

    @Test
    void testEditIfIdFound(){
        PromoCode promoCode2 = new PromoCode();
        promoCode2.setCodeId("7f4313b9-655b-4894-a88a-7f53937a1f84");
        promoCode2.setCodeName("HEMAT40");
        promoCode2.setMinimumPayment((double)5000);
        promoCode2.setDescription("Diskon hebat");

        when(promoCodeRepository.save(promoCode2)).thenReturn(promoCode2);
        when(promoCodeRepository.existsById("7f4313b9-655b-4894-a88a-7f53937a1f84")).thenReturn(true);
        PromoCode result = service.edit(promoCode2);

        verify(promoCodeRepository, times(1)).save(promoCode2);
        assertEquals(promoCode2.getCodeId(), result.getCodeId());
        assertEquals(promoCode2.getCodeName(), result.getCodeName());
    }

    @Test
    void testEditIfIdNotFound(){
        PromoCode promoCode = new PromoCode();
        promoCode.setCodeId("6e282868-9b5b-48a2-b509-0db4aa3615e6");
        when(promoCodeRepository.existsById(any(String.class))).thenReturn(false);

        assertNull(service.edit(promoCode));
        verify(promoCodeRepository, times(1)).existsById(any(String.class));
    }
}
