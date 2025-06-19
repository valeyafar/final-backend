package com.ucc.orders.model.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productID;
    private int quantity;
    private LocalDateTime creationDate;

    @PrePersist
    public void setCreationDate(){
        this.creationDate = LocalDateTime.now();
    }

}
