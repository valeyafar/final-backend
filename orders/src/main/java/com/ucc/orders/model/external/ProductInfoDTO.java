package com.ucc.orders.model.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoDTO {
    private Long id;
    private String name;
    private int stock;
}
