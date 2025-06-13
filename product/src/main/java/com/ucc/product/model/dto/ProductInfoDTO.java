package com.ucc.product.model.dto;

import com.ucc.product.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO  implements Serializable {
    private Long id;
    private String nombre;

    private CategoryInfoDTO categoryInfoDTO;

    public ProductInfoDTO(Long id, String nombre, Category category) {
        this.id = id;
        this.nombre = nombre;
        if (category != null) {
            CategoryInfoDTO categoryInfoDTOAUX = new CategoryInfoDTO();
            categoryInfoDTOAUX.setName(category.getName());
            categoryInfoDTOAUX.setStatus(category.getStatus());
            this.categoryInfoDTO = categoryInfoDTOAUX;
        }
    }


}

