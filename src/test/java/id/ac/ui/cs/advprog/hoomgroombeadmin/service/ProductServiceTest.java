package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import id.ac.ui.cs.advprog.hoomgroombeadmin.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl service;

    Product product1;
    Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        List<String> tags1 = Arrays.asList("vintage", "white", "indoor");
//        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Furniture 1");
        product1.setTag(tags1);
        product1.setDescription("Good Furniture!");
        product1.setPicture("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0");
        product1.setRealPrice(1500000);
        product1.setDiscPrice(1000000);

        product2 = new Product();
        List<String> tags2 = Arrays.asList("metal", "black", "outdoor");
//        product2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Furniture 2");
        product2.setTag(tags2);
        product2.setDescription("Awesome furniture for outdoor activities");
        product2.setPicture("https://th.bing.com/th/id/OIP.jnxbSoE_kf3qaqH8LMDGzAHaHa?rs=1&pid=ImgDetMain");
        product2.setRealPrice(2400000);
        product2.setDiscPrice(2000000);
    }

    @Test
    void testCreateAndFindAll(){
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        service.create(product1);

        when(productRepository.save(any(Product.class))).thenReturn(product2);
        service.create(product2);

        verify(productRepository,times(1)).save(product1);
        verify(productRepository,times(1)).save(product2);

        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productList = service.getAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = products.getFirst();
        assertNotNull(savedProduct);
        assertNotNull(UUID.fromString(savedProduct.getId()));
        assertEquals("Furniture 1", savedProduct.getProductName());
        assertEquals("Good Furniture!", savedProduct.getDescription());
        assertEquals("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0",
                savedProduct.getPicture());
        assertEquals(1500000, savedProduct.getRealPrice());
        assertEquals(1000000, savedProduct.getDiscPrice());
        assertTrue(savedProduct.getTag().contains("vintage"));
        assertTrue(savedProduct.getTag().contains("white"));
        assertTrue(savedProduct.getTag().contains("indoor"));
    }
}
