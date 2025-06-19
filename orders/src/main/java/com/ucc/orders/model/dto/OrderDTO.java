package com.ucc.orders.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotNull(message = "El ID del producto no puede estar vacio")
    private Long productID;

    @Min(value=1, message= "La cantidad debe ser mayor a 0")
    private int quantity;
}
