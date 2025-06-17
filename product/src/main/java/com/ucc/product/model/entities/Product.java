package com.ucc.product.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private Boolean status;

    //Muchos productos tienen una categoría
    @ManyToOne(fetch = FetchType.EAGER, optional = false)

    //@JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
