package id.ac.ui.cs.advprog.hoomgroombeadmin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Product {
    private String id;
    private String productName;
    private List<String> tag;
    private String description;
    private String picture;
    private double realPrice;
    private double discPrice;
}