package com.ucc.product.controller;

import com.ucc.product.model.dto.CategoryCreateDTO;
import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.mappers.CategoriesMappers;
import com.ucc.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;
    private final CategoriesMappers categoriesMappers;

    //Endpoint para obtener todas las categorias (entidad)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    //Endpoin para obtener todas las categorias con dto
    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryInfoDTO> getCategoriesDTO() {
        return categoryService.getAllInfoCategories();
    }

    //Endpoin para obtener por id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryInfoDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    //Endpoint para crear categoria
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> newCategory(@Valid @RequestBody CategoryCreateDTO categoryDTO) {
        Category category = categoriesMappers.toEntity(categoryDTO);
        return categoryService.newCategory(category);
    }

    //Endpoint para actualizar una categoria
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    //Endpoint para eliminar una categoria
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }





}
