package id.ac.ui.cs.advprog.hoomgroombeadmin.repository;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    @Modifying
    @Query(value = "UPDATE product SET sales = sales + :quantity WHERE id = :id", nativeQuery = true)
    int incrementSales(String id, int quantity);
}
