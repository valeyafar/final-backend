package com.ucc.product.model.mappers;

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
        productEntity.setNombre(productDTO.getNombre());
        productEntity.setPrecio(productDTO.getPrecio());
        productEntity.setStatus(Boolean.TRUE);

        Category categoryEntity = categoryRepository.findOneById(productDTO.getCategoryDTO().getId());
        productEntity.setCategory(categoryEntity);
        return productEntity;
    }

    //Convertir entidad a DTO
    public ProductDTO productEntityToDTO(Product productEntity){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNombre(productEntity.getNombre());
        productDTO.setPrecio(productEntity.getPrecio());
        return productDTO;
    }
}
