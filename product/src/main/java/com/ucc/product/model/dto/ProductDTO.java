package com.ucc.product.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    @NotBlank(message = "El nombre no debe estar vacio")
    private String name;

    @NotNull(message = "El precio debe tener un valor asignado")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double price;


    private String description;

    @NotNull(message = "El stock debe tener un valor asignado")
    @Min(value = 0, message = "El stock no debe ser negativo")
    private Integer stock;

    @NotNull(message = "La categoria debe tener un valor asignado")
    private CategoryDTO categoryDTO;
}
