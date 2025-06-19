package com.ucc.product.service;

import com.ucc.product.exceptions.Category.CategoryNotExistException;
import com.ucc.product.exceptions.Product.ProductNotExistException;
import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Category;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.model.mappers.ProductsMappers;
import com.ucc.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository; //Inyeccion del repositorio en nuestro servicio
    private final ProductsMappers productsMappers;

    //Metodo para obtener todos los productos guardados
    public List<Product> getAllProducts() {
        return  productRepository.findAll();
    }

    public List<ProductInfoDTO> getAllInfoProducts(){
        return productRepository.findAll()
                .stream()
                .map(productEntity -> new ProductInfoDTO(productEntity.getId(), productEntity.getName(), productEntity.getCategory()))
                .collect(Collectors.toList());
    }

    //Metodo para obtener los productos por id
    public ProductInfoDTO getProductInfoById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("No existe el producto con ID " + id));

        return productsMappers.productEntityToInfoDTO(product);
    }

    /*public List<Product> getProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }*/

    public List<ProductInfoDTO> getProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(productsMappers::productEntityToInfoDTO)
                .collect(Collectors.toList());
    }



    //Metodo para crear un producto con DTO con mapper
    public ResponseEntity<Void> newProduct(ProductDTO productDTO) {
        Product productEntity = productsMappers.productsDTOToEntity(productDTO);
        productRepository.save(productEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Metodo para actualizar un producto
    public Product updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("No existe el producto con ID " + id));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());

        return productRepository.save(product);
    }

    //Metodo para eliminar un producto
    public ResponseEntity<Object> deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("No existe el producto con ID " + id));

        productRepository.delete(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void discountStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException("No existe el producto con ID " + productId));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para el producto con ID " + productId);
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    public void increaseStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("Producto no encontrado"));
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }



}
