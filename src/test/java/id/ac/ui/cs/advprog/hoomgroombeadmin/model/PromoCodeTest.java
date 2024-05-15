package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class PromoCodeTest {
    PromoCode validPromoCode;
    PromoCode invalidPromoCode;

    @BeforeEach
    void setUp(){
        invalidPromoCode = new PromoCode();
    }

    @Test
    void testValidSetGetCodeName(){
        validPromoCode = new PromoCode("HEMAT20",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        assertEquals("HEMAT20", validPromoCode.getCodeName());

        validPromoCode.setCodeName("diskon40");
        assertEquals("DISKON40", validPromoCode.getCodeName());
        assertEquals(40, validPromoCode.getDiscPercentage());
    }

    @Test
    void testValidSetGetId(){
        validPromoCode = new PromoCode("BELANJAHEMAT20",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        validPromoCode.setCodeId("59c7e050-a8c4-4c6d-b9f8-ec6cb8241340");
        assertEquals("59c7e050-a8c4-4c6d-b9f8-ec6cb8241340", validPromoCode.getCodeId());
    }

    @Test
    void testGetDiscPercent(){
        validPromoCode = new PromoCode("BELANJA20",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        assertEquals(20, validPromoCode.getDiscPercentage());
    }

    @Test
    void testSetGetDesc(){
        validPromoCode = new PromoCode("DISKON20",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        assertEquals("Promo Kode pertama", validPromoCode.getDescription());
        validPromoCode.setDescription("Deskripsi baru");
        assertEquals("Deskripsi baru", validPromoCode.getDescription());
    }

    @Test
    void testValidSetGetMinPayment(){
        validPromoCode = new PromoCode("BELANJAHEMAT10",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        assertEquals(150000, validPromoCode.getMinimumPayment());

        validPromoCode.setMinimumPayment((double)(20000));
        assertEquals(20000, validPromoCode.getMinimumPayment());
    }

    @Test
    void testValidSetGetEndDate(){
        validPromoCode = new PromoCode("BELANJAHEMAT30",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));
        assertEquals(LocalDate.of(2024, 12, 31), validPromoCode.getEndDate());

        validPromoCode.setEndDate(LocalDate.of(2024, 6, 30));
        assertEquals(LocalDate.of(2024, 6, 30), validPromoCode.getEndDate());
    }

    @Test
    void testInvalidName(){
        assertThrows(IllegalArgumentException.class, () -> {new PromoCode("20",
                LocalDate.of(2024, 12, 31),
                "Promo Kode pertama",
                Double.parseDouble("150000"));});
        assertThrows(IllegalArgumentException.class, () -> {invalidPromoCode.setCodeName("BELANJA20HEMAT");});
        assertThrows(IllegalArgumentException.class, () -> {invalidPromoCode.setCodeName("BELANJA2HEMAT5");});
    }
    
    @Test
    void testInvalidId(){
        assertThrows(IllegalArgumentException.class, () -> {invalidPromoCode.setCodeId("1");});
    }

    @Test
    void testInvalidMinPayemnt(){
        assertThrows(IllegalArgumentException.class, () ->
                {new PromoCode("BELANJAHEMAT20",
                            LocalDate.of(2024, 12, 31),
                            "Promo Kode pertama",
                            Double.parseDouble("-100"));
                });
        assertThrows(IllegalArgumentException.class, () -> {invalidPromoCode.setMinimumPayment(Double.parseDouble("0"));});
    }

    @Test
    void testInvalidEndDate(){
        assertThrows(IllegalArgumentException.class, () -> {
            new PromoCode("BELANJAHEMAT20",
                    LocalDate.of(2024, 2, 29),
                    "Promo Kode pertama",
                    Double.parseDouble("150000"));
        });
        assertThrows(IllegalArgumentException.class, () -> {invalidPromoCode.setEndDate(LocalDate.of(2023, 12, 31));});
    }
}
