package com.ucc.product.model.mappers;

import com.ucc.product.model.dto.CategoryCreateDTO;
import com.ucc.product.model.dto.CategoryDTO;
import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoriesMappers {

    //Convertir un DTO a una entidad
    public Category toEntity(CategoryCreateDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setStatus(true);
        return category;
    }

    //Category a CategoryDTO
    public CategoryDTO categoryEntityToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        return dto;
    }


    //Category a CategoryInfoDTO
    public CategoryInfoDTO categoryEntityToInfoDTO(Category category) {
        CategoryInfoDTO dto = new CategoryInfoDTO();
        dto.setName(category.getName());
        dto.setStatus(category.getStatus());
        return dto;
    }

}
