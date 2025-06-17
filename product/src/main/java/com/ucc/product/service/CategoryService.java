package com.ucc.product.service;

import com.ucc.product.exceptions.Category.CategoryNotExistException;
import com.ucc.product.exceptions.Product.ProductNotExistException;
import com.ucc.product.model.dto.CategoryCreateDTO;
import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.mappers.CategoriesMappers;
import com.ucc.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoriesMappers categoriesMappers;

    //Metodo para obtener todos las categorias guardadas
    public List<Category> getCategories() {
        return  categoryRepository.findAll();
    }

    public List<CategoryInfoDTO> getAllInfoCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoriesMappers::categoryEntityToInfoDTO)
                .collect(Collectors.toList());
    }

    //Metodo para obtener las categorias por id
    public CategoryInfoDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotExistException("No existe la categoría con ID " + id));

        return categoriesMappers.categoryEntityToInfoDTO(category);
    }


    public ResponseEntity<Object> newCategory(Category category) {
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    public ResponseEntity<Object> updateCategory(Long id, CategoryCreateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotExistException("No existe la categoría con ID " + id));

        category.setName(dto.getName());
        categoryRepository.save(category);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotExistException("No existe la categoría con ID " + id));

        categoryRepository.delete(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}