package com.ucc.product.repository;
import com.ucc.product.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findOneById(Long id);
}
