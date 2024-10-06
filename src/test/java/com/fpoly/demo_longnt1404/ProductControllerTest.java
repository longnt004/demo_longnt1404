package com.fpoly.demo_longnt1404;
import com.fpoly.demo_longnt1404.controller.ProductController;
import com.fpoly.demo_longnt1404.model.Product;
import com.fpoly.demo_longnt1404.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_HappyCase() {
        Page<Product> products = new PageImpl<>(Collections.singletonList(new Product()));
        when(productService.findAll(anyInt(), anyInt())).thenReturn(products);

        Page<Product> result = productController.getAllProducts(0, 10);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAllProducts_EmptyPage() {
        Page<Product> products = new PageImpl<>(Collections.emptyList());
        when(productService.findAll(anyInt(), anyInt())).thenReturn(products);

        Page<Product> result = productController.getAllProducts(0, 10);

        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getAllProducts_InvalidPageSize() {
        when(productService.findAll(anyInt(), eq(-1))).thenThrow(new IllegalArgumentException("Invalid page size"));

        try {
            productController.getAllProducts(0, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid page size", e.getMessage());
        }
    }

    @Test
    void getAllProducts_PageOutOfBounds() {
        when(productService.findAll(eq(100), anyInt())).thenThrow(new IndexOutOfBoundsException("Page out of bounds"));

        try {
            productController.getAllProducts(100, 10);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Page out of bounds", e.getMessage());
        }
    }

    @Test
    void getProductById_HappyCase() {
        Product product = new Product();
        when(productService.findById(anyString())).thenReturn(product);

        Product result = productController.getProductById("1");

        assertEquals(product, result);
    }

    @Test
    void getProductById_ProductDoesNotExist() {
        when(productService.findById(anyString())).thenReturn(null);

        Product result = productController.getProductById("1");

        assertEquals(null, result);
    }

    @Test
    void getProductById_InvalidId() {
        when(productService.findById(eq("invalid"))).thenThrow(new IllegalArgumentException("Invalid ID"));

        try {
            productController.getProductById("invalid");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid ID", e.getMessage());
        }
    }

    @Test
    void getProductById_EmptyId() {
        when(productService.findById(eq(""))).thenThrow(new IllegalArgumentException("ID cannot be empty"));

        try {
            productController.getProductById("");
        } catch (IllegalArgumentException e) {
            assertEquals("ID cannot be empty", e.getMessage());
        }
    }

    @Test
    void createProduct_HappyCase() {
        Product product = new Product();
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    void createProduct_InvalidProduct() {
        when(productService.createProduct(any(Product.class))).thenThrow(new RuntimeException("Invalid product"));

        try {
            productController.createProduct(new Product());
        } catch (RuntimeException e) {
            assertEquals("Invalid product", e.getMessage());
        }
    }

    @Test
    void createProduct_NullProduct() {
        when(productService.createProduct(null)).thenThrow(new IllegalArgumentException("Product cannot be null"));

        try {
            productController.createProduct(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Product cannot be null", e.getMessage());
        }
    }

    @Test
    void createProduct_EmptyProduct() {
        Product emptyProduct = new Product();
        when(productService.createProduct(eq(emptyProduct))).thenThrow(new IllegalArgumentException("Product details cannot be empty"));

        try {
            productController.createProduct(emptyProduct);
        } catch (IllegalArgumentException e) {
            assertEquals("Product details cannot be empty", e.getMessage());
        }
    }

    @Test
    void updateProduct_HappyCase() {
        Product product = new Product();
        when(productService.updateProduct(anyString(), any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct("1", product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
    }

    @Test
    void updateProduct_ProductDoesNotExist() {
        when(productService.updateProduct(anyString(), any(Product.class))).thenThrow(new RuntimeException("Product not found"));

        try {
            productController.updateProduct("1", new Product());
        } catch (RuntimeException e) {
            assertEquals("Product not found", e.getMessage());
        }
    }

    @Test
    void updateProduct_InvalidId() {
        when(productService.updateProduct(eq("invalid"), any(Product.class))).thenThrow(new IllegalArgumentException("Invalid ID"));

        try {
            productController.updateProduct("invalid", new Product());
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid ID", e.getMessage());
        }
    }

    @Test
    void updateProduct_NullProduct() {
        when(productService.updateProduct(anyString(), eq(null))).thenThrow(new IllegalArgumentException("Product cannot be null"));

        try {
            productController.updateProduct("1", null);
        } catch (IllegalArgumentException e) {
            assertEquals("Product cannot be null", e.getMessage());
        }
    }

    @Test
    void deleteProduct_HappyCase() {
        doNothing().when(productService).deleteProduct(anyString());

        productController.deleteProduct("1");

        verify(productService, times(1)).deleteProduct("1");
    }

    @Test
    void deleteProduct_ProductDoesNotExist() {
        doThrow(new RuntimeException("Product not found")).when(productService).deleteProduct(anyString());

        try {
            productController.deleteProduct("1");
        } catch (RuntimeException e) {
            assertEquals("Product not found", e.getMessage());
        }
    }

    @Test
    void deleteProduct_InvalidId() {
        doThrow(new IllegalArgumentException("Invalid ID")).when(productService).deleteProduct(eq("invalid"));

        try {
            productController.deleteProduct("invalid");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid ID", e.getMessage());
        }
    }

    @Test
    void deleteProduct_EmptyId() {
        doThrow(new IllegalArgumentException("ID cannot be empty")).when(productService).deleteProduct(eq(""));

        try {
            productController.deleteProduct("");
        } catch (IllegalArgumentException e) {
            assertEquals("ID cannot be empty", e.getMessage());
        }
    }
}
