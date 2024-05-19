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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl service;

    Product product1;

    @BeforeEach
    void setUp() {
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
    void testCreateAndFindAll(){
        when(productRepository.save(any(Product.class))).thenReturn(product1);
        product1.setId("6f42392e-40a2-475a-9c00-c667307c20d8");
        service.save(product1);

        verify(productRepository,times(1)).save(product1);

        List<Product> products = Arrays.asList(product1);
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
        assertEquals(0L, savedProduct.getSales());
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
        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(Optional.ofNullable(product1));

        Product savedProduct = service.getProductById("eb558e9f-1c39-460e-8860-71af6af63bd6");

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
        assertNull(service.getProductById("0000"));
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
        Product result = service.save(product1);

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
    void testFilterByLowestPriceIfAmountBigger(){
        Product product2 = new Product();
        product2.setRealPrice(1000000);

        Product product3 = new Product();
        product3.setRealPrice(2000000);

        List<Product> products = Arrays.asList(product1, product2, product3);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByPrice(10, true);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1000000, result.getFirst().getRealPrice());
        assertEquals(2000000, result.getLast().getRealPrice());
    }

    @Test
    void testFilterByHighestPriceIfAmountBigger(){
        Product product2 = new Product();
        product2.setRealPrice(1000000);

        Product product3 = new Product();
        product3.setRealPrice(2000000);

        List<Product> products = Arrays.asList(product1, product2, product3);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByPrice(10, false);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(2000000, result.getFirst().getRealPrice());
        assertEquals(1000000, result.getLast().getRealPrice());
    }

    @Test
    void testFilterLowestPriceIfAmountSmaller() {
        Product product2 = new Product();
        product2.setRealPrice(1000000);

        Product product3 = new Product();
        product3.setRealPrice(1500000);

        Product product4 = new Product();
        product4.setRealPrice(3000000);

        Product product5 = new Product();
        product5.setRealPrice(2000000);

        Product product6 = new Product();
        product6.setRealPrice(5000000);

        Product product7 = new Product();
        product7.setRealPrice(2500000);

        Product product8 = new Product();
        product8.setRealPrice(900000);

        Product product9 = new Product();
        product9.setRealPrice(3500000);

        Product product10 = new Product();
        product10.setRealPrice(4000000);

        Product product11 = new Product();
        product11.setRealPrice(5500000);

        List<Product> products = Arrays.asList(product1, product2, product3,
                product4, product5, product6, product7, product8, product9, product10, product11);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByPrice(8, true);
        assertEquals(8, result.size());
        assertEquals(900000, result.getFirst().getRealPrice());
        assertEquals(1000000, result.get(1).getRealPrice());
        assertEquals(3500000, result.getLast().getRealPrice());
    }

    @Test
    void testFilterHighestPriceIfAmountSmaller(){
        Product product2 = new Product();
        product2.setRealPrice(1000000);

        Product product3 = new Product();
        product3.setRealPrice(1500000);

        Product product4 = new Product();
        product4.setRealPrice(3000000);

        Product product5 = new Product();
        product5.setRealPrice(2000000);

        Product product6 = new Product();
        product6.setRealPrice(5000000);

        Product product7 = new Product();
        product7.setRealPrice(2500000);

        Product product8 = new Product();
        product8.setRealPrice(900000);

        Product product9 = new Product();
        product9.setRealPrice(3500000);

        Product product10 = new Product();
        product10.setRealPrice(4000000);

        Product product11 = new Product();
        product11.setRealPrice(5500000);

        List<Product> products = Arrays.asList(product1, product2, product3,
                product4, product5, product6, product7, product8, product9, product10, product11);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByPrice(8, false);
        assertEquals(8, result.size());
        assertEquals(5500000, result.getFirst().getRealPrice());
        assertEquals(5000000, result.get(1).getRealPrice());
        assertEquals(1500000, result.getLast().getRealPrice());
    }

    @Test
    void testFilterByLowestSalesIfAmountBigger(){
        product1.setSales(30);

        Product product2 = new Product();
        product2.setSales(100);

        Product product3 = new Product();
        product3.setSales(50);

        List<Product> products = Arrays.asList(product1, product2, product3);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsBySales(10, true);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(30, result.getFirst().getSales());
        assertEquals(100, result.getLast().getSales());
    }

    @Test
    void testFilterByHighestSalesIfAmountBigger(){
        product1.setSales(30);

        Product product2 = new Product();
        product2.setSales(100);

        Product product3 = new Product();
        product3.setSales(50);

        List<Product> products = Arrays.asList(product1, product2, product3);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsBySales(10, false);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(100, result.getFirst().getSales());
        assertEquals(30, result.getLast().getSales());
    }

    @Test
    void testFilterByHighestSalesIfAmountSmaller(){
        product1.setSales(30);

        Product product2 = new Product();
        product2.setSales(100);

        Product product3 = new Product();
        product3.setSales(50);

        Product product4 = new Product();
        product4.setSales(20);

        Product product5 = new Product();
        product5.setSales(80);

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsBySales(3, false);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(100, result.getFirst().getSales());
        assertEquals(50, result.getLast().getSales());
    }

    @Test
    void testFilterByLowestSalesIfAmountSmaller(){
        product1.setSales(30);

        Product product2 = new Product();
        product2.setSales(100);

        Product product3 = new Product();
        product3.setSales(50);

        Product product4 = new Product();
        product4.setSales(20);

        Product product5 = new Product();
        product5.setSales(80);

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsBySales(3, true);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(20, result.getFirst().getSales());
        assertEquals(50, result.getLast().getSales());
    }

   @Test
   void testFilterIfEmpty(){
       when(productRepository.findAll()).thenReturn(new ArrayList<Product>());
       List<Product> result = service.getProductsByPrice(10, false);

       assertNotNull(result);
       assertEquals(0, result.size());
   }

   @Test
   void testSearchIfEmpty(){
       when(productRepository.findByProductNameContainingIgnoreCase("Test")).thenReturn(new ArrayList<Product>());
       List<Product> result = service.getProductsBySearched(10, false, "Test");

       assertNotNull(result);
       assertEquals(0, result.size());
   }

   @Test
   void testSearchedKeywordNotEmptyAscending() {
       Product searchedProduct1 = new Product();
       searchedProduct1.setProductName("Furry 1");

       Product searchedProduct2 = new Product();
       searchedProduct2.setProductName("Furry 2");

       Product searchedProduct3 = new Product();
       searchedProduct3.setProductName("Furry 3");

       List<Product> products = Arrays.asList(searchedProduct2, searchedProduct1, searchedProduct3);
       when(productRepository.findByProductNameContainingIgnoreCase("Furry")).thenReturn(products);

       List<Product> result = service.getProductsBySearched(3, true, "Furry");

       assertEquals(3, result.size());
       assertEquals("Furry 1", result.getFirst().getProductName());
       assertEquals("Furry 2", result.get(1).getProductName());
       assertEquals("Furry 3", result.getLast().getProductName());
   }

    void testSearchedKeywordNotEmptyDescending() {
        Product searchedProduct1 = new Product();
        searchedProduct1.setProductName("Furry 1");

        Product searchedProduct2 = new Product();
        searchedProduct2.setProductName("Furry 2");

        Product searchedProduct3 = new Product();
        searchedProduct3.setProductName("Furry 3");

        List<Product> products = Arrays.asList(searchedProduct2, searchedProduct1, searchedProduct3);
        when(productRepository.findByProductNameContainingIgnoreCase("Furry")).thenReturn(products);

        List<Product> result = service.getProductsBySearched(3, false, "Furry");

        assertEquals(3, result.size());
        assertEquals("Furry 3", result.getFirst().getProductName());
        assertEquals("Furry 2", result.get(1).getProductName());
        assertEquals("Furry 1", result.getLast().getProductName());
    }

    @Test
    void testFilterByTagAscending() {
        Product taggedProduct1 = new Product();
        List<String> tag1 = Arrays.asList("vintage", "white", "indoor");
        taggedProduct1.setProductName("Product1");
        taggedProduct1.setTag(tag1);

        Product taggedProduct2 = new Product();
        List<String> tag2 = Arrays.asList("white", "vintage", "indoor");
        taggedProduct2.setProductName("Product2");
        taggedProduct2.setTag(tag2);

        List<Product> products = Arrays.asList(taggedProduct1, taggedProduct2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByTag(2, true);
        assertEquals(2, result.size());
        assertEquals("Product1", result.getFirst().getProductName());
        assertEquals("Product2", result.get(1).getProductName());

    }

    @Test
    void testFilterByTagDescending() {
        Product taggedProduct1 = new Product();
        List<String> tag1 = Arrays.asList("vintage", "white", "indoor");
        taggedProduct1.setProductName("Product1");
        taggedProduct1.setTag(tag1);

        Product taggedProduct2 = new Product();
        List<String> tag2 = Arrays.asList("white", "vintage", "indoor");
        taggedProduct2.setProductName("Product2");
        taggedProduct2.setTag(tag2);

        List<Product> products = Arrays.asList(taggedProduct1, taggedProduct2);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = service.getProductsByTag(2, false);
        assertEquals(2, result.size());
        assertEquals("Product2", result.getFirst().getProductName());
        assertEquals("Product1", result.get(1).getProductName());

    }

    @Test
    void incrementSalesSuccess() throws ExecutionException, InterruptedException {
        String productId = "ABC123";
        int quantity = 3;
        when(productRepository.incrementSales(anyString(), anyInt())).thenReturn(1);

        CompletableFuture<Integer> future = service.incrementSales(productId, quantity);
        int result = future.get();

        assertEquals(1, result);
    }

    @Test
    void incrementSalesFailed() throws ExecutionException, InterruptedException {
        String productId = "ABC123";
        int quantity = 3;
        when(productRepository.incrementSales(anyString(), anyInt())).thenReturn(0);

        CompletableFuture<Integer> future = service.incrementSales(productId, quantity);
        int result = future.get();

        assertEquals(0, result);
    }
}
