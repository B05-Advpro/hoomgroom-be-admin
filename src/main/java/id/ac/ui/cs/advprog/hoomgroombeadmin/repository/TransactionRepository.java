package id.ac.ui.cs.advprog.hoomgroombeadmin.repository;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
