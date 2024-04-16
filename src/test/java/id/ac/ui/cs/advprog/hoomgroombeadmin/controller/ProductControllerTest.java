package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.controller.ProductController;

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
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private ProductController controller;

    @Test
    public void createProductTest() throws Exception {
        mvc.perform(post("/admin/product/create"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductPostTest() throws Exception {
        mvc.perform(post("/admin/product/update/save"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductPageTest() throws Exception {
        UUID productId = new UUID(32, 10);
        mvc.perform(get("/admin/product/update/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductTest() throws Exception {
        UUID productId = new UUID(32, 10);
        mvc.perform(post("/admin/product/delete/" + productId))
                .andExpect(status().isOk());
    }

    @Test
    public void listProductTest() throws Exception {
        mvc.perform(get("/product/list/"))
                .andExpect(status().isOk());
    }
}
