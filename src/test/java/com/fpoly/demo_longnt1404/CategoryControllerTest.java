package com.fpoly.demo_longnt1404;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpoly.demo_longnt1404.controller.CategoryController;
import com.fpoly.demo_longnt1404.exception.GlobalExceptionHandler;
import com.fpoly.demo_longnt1404.model.Category;
import com.fpoly.demo_longnt1404.service.CategoryService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    public void testGetAllCategories_HappyCase() throws Exception {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()));
    }

    @Test
    public void testGetAllCategories_EmptyList() throws Exception {
        when(categoryService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetAllCategories_ServiceThrowsException() throws Exception {
        when(categoryService.findAll()).thenThrow(new ServiceException("Service error"));

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testCreateCategory_HappyCase() throws Exception {
        Category category = new Category();
        category.setName("New Category");
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    public void testCreateCategory_InvalidInput() throws Exception {

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Map.of("name", "").toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateCategory_HappyCase() throws Exception {
        Category category = new Category();
        category.setName("Updated Category");
        when(categoryService.update(anyLong(), any(Category.class))).thenReturn(category);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    public void testUpdateCategory_NotFound() throws Exception {
        when(categoryService.update(anyLong(), any(Category.class))).thenThrow(new RuntimeException("Category not found"));

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Category\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteCategory_HappyCase() throws Exception {
        doNothing().when(categoryService).deleteById(anyLong());

        mockMvc.perform(delete("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
