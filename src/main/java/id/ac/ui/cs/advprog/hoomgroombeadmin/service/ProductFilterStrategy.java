package id.ac.ui.cs.advprog.hoomgroombeadmin.service;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.Product;
import java.util.List;

public interface ProductFilterStrategy {
    List<Product> sort(List<Product> products);
}
