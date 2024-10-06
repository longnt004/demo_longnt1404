package com.fpoly.demo_longnt1404.service.impl;

import com.fpoly.demo_longnt1404.model.Product;
import com.fpoly.demo_longnt1404.repository.ProductRepository;
import com.fpoly.demo_longnt1404.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id,Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setTitle(productDetails.getTitle());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setDescription(productDetails.getDescription());
            product.setImages(productDetails.getImages());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void deleteProduct(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        }else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public Page<Product> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
