package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.hoomgroombeadmin.controller.ProductRestController;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductRestController controller;

    ObjectMapper objectMapper = new ObjectMapper();
    Product product1;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        product1 = new Product();
        List<String> tags1 = Arrays.asList("vintage", "white", "indoor");
        product1.setProductName("Furniture 1");
        product1.setTag(tags1);
        product1.setDescription("Good Furniture!");
        product1.setPicture("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0");
        product1.setRealPrice(1500000);
        product1.setDiscPrice(1000000);
    }

    @Test
    public void createProductTest() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(product1);
        mvc.perform(post("/admin/product/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {Product product = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
                    assertEquals(product1.getProductName(), product.getProductName());
                    assertEquals(product1.getPicture(), product.getPicture());
                    assertEquals(product1.getDescription(), product.getDescription());
                    assertEquals(product1.getRealPrice(), product.getRealPrice());
                    assertEquals(product1.getDiscPrice(), product.getDiscPrice());
                    assertTrue(product.getTag().contains("vintage"));
                    assertTrue(product.getTag().contains("white"));
                    assertTrue(product.getTag().contains("indoor"));
                });

        verify(productService,times(1)).create(any(Product.class));
    }

    @Test
    public void updateProductPostTest() throws Exception {
        when(productService.edit(any(Product.class))).thenReturn(product1);
        mvc.perform(post("/admin/product/update/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(result -> {Product product = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
                assertEquals(product1.getProductName(), product.getProductName());
                assertEquals(product1.getPicture(), product.getPicture());
                assertEquals(product1.getDescription(), product.getDescription());
                assertEquals(product1.getRealPrice(), product.getRealPrice());
                assertEquals(product1.getDiscPrice(), product.getDiscPrice());
                assertTrue(product.getTag().contains("vintage"));
                assertTrue(product.getTag().contains("white"));
                assertTrue(product.getTag().contains("indoor"));
            });
        verify(productService, times(1)).edit(any(Product.class));
    }

    @Test
    public void updateProductPageTest() throws Exception {
        UUID productId = new UUID(32, 10);
        product1.setId(productId.toString());
        when(productService.getProductById(productId.toString())).thenReturn(product1);

        mvc.perform(get("/admin/product/update/" + productId.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(result -> {Product product = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
                assertEquals(productId.toString(), product.getId());
                assertEquals(product1.getProductName(), product.getProductName());
                assertEquals(product1.getPicture(), product.getPicture());
                assertEquals(product1.getDescription(), product.getDescription());
                assertEquals(product1.getRealPrice(), product.getRealPrice());
                assertEquals(product1.getDiscPrice(), product.getDiscPrice());
                assertTrue(product.getTag().contains("vintage"));
                assertTrue(product.getTag().contains("white"));
                assertTrue(product.getTag().contains("indoor"));
            });
        verify(productService, times(1)).getProductById(productId.toString());
    }

    @Test
    public void deleteProductTest() throws Exception {
        UUID productId = new UUID(32, 10);
        String expectedResult = "Deleted product with ID " + productId;
        when(productService.delete(productId.toString())).thenReturn(productId.toString());
        mvc.perform(delete("/admin/product/delete/" + productId.toString()))
                .andExpect(status().isOk())
                .andDo(result -> {String responseBody = result.getResponse().getContentAsString();
                assertEquals(expectedResult, responseBody);});
    }

    @Test
    public void listProductTest() throws Exception {
        when(productService.getAll()).thenReturn(Arrays.asList(product1));
        mvc.perform(get("/admin/product/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {List<Product> products = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Product>>() {});
                    assertNotNull(products);
                    Product product = products.getFirst();
                    assertEquals(product1.getProductName(), product.getProductName());
                    assertEquals(product1.getPicture(), product.getPicture());
                    assertEquals(product1.getDescription(), product.getDescription());
                    assertEquals(product1.getRealPrice(), product.getRealPrice());
                    assertEquals(product1.getDiscPrice(), product.getDiscPrice());
                    assertTrue(product.getTag().contains("vintage"));
                    assertTrue(product.getTag().contains("white"));
                    assertTrue(product.getTag().contains("indoor"));
                });
    }
}
