package com.fpoly.demo_longnt1404.service;

import com.fpoly.demo_longnt1404.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(String id ,Product product);
    void deleteProduct(String id);
    Page<Product> findAll(int page, int size);
    Product findById(String id);
}
