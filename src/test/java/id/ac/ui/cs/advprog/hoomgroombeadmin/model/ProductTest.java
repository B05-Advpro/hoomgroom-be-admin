package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class ProductTest{
    Product product;
    @BeforeEach
    void setUp(){
        this.product = new Product();
        List<String> tags = Arrays.asList("vintage", "white", "indoor");
        this.product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Furniture 1");
        this.product.setTag(tags);
        this.product.setDescription("Good Furniture!");
        this.product.setPicture("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0");
        this.product.setRealPrice(1500000);
        this.product.setDiscPrice(1000000);
        this.product.setSales(1);
    }

    @Test
    void testGetId(){
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getId());
    }

    @Test
    void testGetProductName(){
        assertEquals("Furniture 1", this.product.getProductName());
    }
    
    @Test
    void testGetTag(){
        List<String> listTags = this.product.getTag();
        assertEquals("vintage", listTags.get(0));
        assertEquals("white", listTags.get(1));
        assertEquals("indoor", listTags.get(2));
    }
    
    @Test
    void testGetDescription(){
        assertEquals("Good Furniture!", this.product.getDescription());
    }

    @Test
    void testGetPicture(){
        assertEquals("https://th.bing.com/th/id/R.9d24e1528d7ee3c412d6711744221414?rik=5X%2fhugoJOfiwDA&pid=ImgRaw&r=0", this.product.getPicture());
    }

    @Test
    void testGetRealPrice(){
        assertEquals(1500000, this.product.getRealPrice());
    }

    @Test
    void testGetDiscPrice(){
        assertEquals(1000000, this.product.getDiscPrice());
    }

    @Test
    void testGetSales(){
        assertEquals(1, this.product.getSales());
    }
}
