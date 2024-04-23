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

import java.util.*;

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

    @Test
    void testDelete(){
        String productId = (new UUID(32, 10)).toString();
        when(productRepository.existsById(productId)).thenReturn(true);

        String result = service.delete(productId);
        verify(productRepository,times(1)).existsById(productId);
        verify(productRepository, times(1)).deleteById(productId);
        assertEquals(productId, result);
    }

    @Test
    void testDeleteIfIdNotFound(){
        String productId = (new UUID(32, 10)).toString();
        when(productRepository.existsById(productId)).thenReturn(false);

        assertNull(service.delete(productId));
        verify(productRepository,times(1)).existsById(productId);
    }

    @Test
    void testGetProductByIdFound(){
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        when(productRepository.existsById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(true);
        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.ofNullable(product1));

        Product savedProduct = service.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(productRepository,times(1)).existsById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        verify(productRepository,times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", savedProduct.getId());
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

    @Test
    void testGetProductByIdNotFound(){
        when(productRepository.existsById("0000")).thenReturn(false);

        assertNull(service.getProductById("0000"));
        verify(productRepository,times(1)).existsById("0000");
    }

    @Test
    void testEditIfIdFound(){
        product1.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        List<String> tags = Arrays.asList("metal", "black", "outdoor");
        product1.setProductName("Furniture 2");
        product1.setTag(tags);
        product1.setDescription("Awesome furniture for outdoor activities");
        product1.setPicture("https://th.bing.com/th/id/OIP.jnxbSoE_kf3qaqH8LMDGzAHaHa?rs=1&pid=ImgDetMain");
        product1.setRealPrice(2400000);
        product1.setDiscPrice(2000000);

        when(productRepository.save(product1)).thenReturn(product1);
        when(productRepository.existsById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(true);
        Product result = service.edit(product1);

        verify(productRepository,times(1)).save(product1);
        assertEquals(product1.getId(), result.getId());
        assertEquals(product1.getProductName(), result.getProductName());
        assertEquals(product1.getDescription(), result.getDescription());
        assertEquals(product1.getPicture(), result.getPicture());
        assertEquals(product1.getDiscPrice(), result.getDiscPrice());
        assertEquals(product1.getRealPrice(), result.getRealPrice());
        assertEquals(product1.getTag().getFirst(), product1.getTag().getFirst());
        assertEquals(product1.getTag().getLast(), product1.getTag().getLast());
    }

    @Test
    void testEditIfIdNotFound(){
        Product product = new Product();
        product.setId("11111");
        when(productRepository.existsById("11111")).thenReturn(false);

        assertNull(service.edit(product));
        verify(productRepository, times(1)).existsById("11111");
    }
}
