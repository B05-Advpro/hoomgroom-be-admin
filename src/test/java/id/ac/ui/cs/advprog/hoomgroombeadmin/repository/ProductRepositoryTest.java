//package id.ac.ui.cs.advprog.hoomgroombeadmin.repository;
//
//import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Transactional
//@Commit
//class ProductRepositoryTest {

//    @Autowired
//    private ProductRepository productRepository;
//
//    @Test
//    void testIncrementSalesSuccess() {
//        Product product1 = new Product();
//        List<String> tags1 = Arrays.asList("vintage", "white", "indoor");
//        product1.setProductName("Furniture 1");
//        product1.setTag(tags1);
//        product1.setDescription("Good Furniture!");
//        product1.setPicture("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0");
//        product1.setRealPrice(1500000);
//        product1.setDiscPrice(1000000);
//        Product savedProduct = productRepository.save(product1);
//
//        int result = productRepository.incrementSales(savedProduct.getId(), 5);
//        assertEquals(1, result);
//    }
//
//    @Test
//    void testIncrementSalesFails() {
//        int result = productRepository.incrementSales("ABC123", 5);
//        assertEquals(0, result);
//    }
//}