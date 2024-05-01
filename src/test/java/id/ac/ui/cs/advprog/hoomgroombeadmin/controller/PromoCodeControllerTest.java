package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.controller.PromoCodeController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PromoCodeController.class)
public class PromoCodeControllerTest {
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private PromoCodeController controller;

    @Test
    public void createPromoCodeTest() throws Exception {
        mvc.perform(post("/admin/promo-code/create"))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePromoCodePostTest() throws Exception {
        mvc.perform(post("/admin/promo-code/update/save"))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePromoCodePageTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        mvc.perform(get("/admin/promo-code/update/" + promoCodeId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePromoCodeTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        mvc.perform(post("/admin/promo-code/delete/" + promoCodeId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void listPromoCodeTest() throws Exception {
        mvc.perform(get("/admin/promo-code/manage"))
                .andExpect(status().isOk());
    }
}
