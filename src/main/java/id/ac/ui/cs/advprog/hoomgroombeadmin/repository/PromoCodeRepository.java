package id.ac.ui.cs.advprog.hoomgroombeadmin.repository;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, String>{
}
