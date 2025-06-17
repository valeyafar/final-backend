package com.ucc.product.model.mappers;

import com.ucc.product.exceptions.Category.CategoryNotExistException;
import com.ucc.product.model.dto.CategoryDTO;
import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductsMappers {

    //Convertir un DTO a una entidad
    private final CategoryRepository categoryRepository;
    public Product productsDTOToEntity(ProductDTO productDTO){
        Product productEntity = new Product();
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setStock(productDTO.getStock());
        productEntity.setStatus(Boolean.TRUE);

        Long categoryId = productDTO.getCategoryDTO().getId();
        Category categoryEntity = categoryRepository.findOneById(categoryId);

        if (categoryEntity == null) {
            throw new CategoryNotExistException("No existe la categoría con ID " + categoryId);
        }

        productEntity.setCategory(categoryEntity);
        return productEntity;
    }

    //Convertir entidad a DTO
    public ProductDTO productEntityToDTO(Product productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productEntity.getName());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setStock(productEntity.getStock());

        Category category = productEntity.getCategory();
        if (category != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            productDTO.setCategoryDTO(categoryDTO);
        }

        return productDTO;
    }

    //Convertir entidad a InfoDTO
    public ProductInfoDTO productEntityToInfoDTO(Product productEntity){
        ProductInfoDTO dto = new ProductInfoDTO();
        dto.setId(productEntity.getId());
        dto.setName(productEntity.getName());

        if (productEntity.getCategory() != null) {
            CategoryInfoDTO categoryInfo = new CategoryInfoDTO();
            categoryInfo.setId(productEntity.getCategory().getId());
            categoryInfo.setName(productEntity.getCategory().getName());
            categoryInfo.setStatus(productEntity.getCategory().getStatus());
            dto.setCategoryInfoDTO(categoryInfo);
        }

        return dto;
    }

}
