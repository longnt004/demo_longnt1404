package com.fpoly.demo_longnt1404.service;


import com.fpoly.demo_longnt1404.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    void deleteById(Long id);
}
