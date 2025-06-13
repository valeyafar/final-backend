package com.ucc.product.service;


import com.ucc.product.exceptions.Product.ProductNotExistException;
import com.ucc.product.model.dto.ProductInfoDTO;
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

    //Metodo para obtener productos por id
    /*public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }*/

    public Product getProductById(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            throw new ProductNotExistException("No se encontró el producto con el id: " + id);
        }else {
            return productOptional.get();
        }
    }

    //Metodo para crear un producto
    /*public void createProduct(Product product){
        productRepository.save(product);
    }*/

    //Metodo para crear un producto con DTO sin mapper
    /*public ResponseEntity<Void> newProduct(ProductDTO productDTO) {
        Product productEntity = new Product();
        productEntity.setNombre(productDTO.getNombre());
        productEntity.setPrecio(productDTO.getPrecio());
        productEntity.setStatus(Boolean.TRUE);
        productRepository.save(productEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/

    //Metodo para crear un producto con DTO con mapper
    public ResponseEntity<Void> newProduct(ProductDTO productDTO) {
        Product productEntity = productsMappers.productsDTOToEntity(productDTO);
        productRepository.save(productEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Metodo para actualizar un producto
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        product.setNombre(productDetails.getNombre());
        product.setPrecio(productDetails.getPrecio());
        product.setDescripcion(productDetails.getDescripcion());
        product.setStock(productDetails.getStock());
        product.setStatus(productDetails.getStatus());

        return productRepository.save(product);
    }

    //Metodo para eliminar un producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<ProductInfoDTO> getAllInfoProducts(){
        return productRepository.findAll()
                .stream()
                .map(productEntity -> new ProductInfoDTO(productEntity.getId(), productEntity.getNombre(), productEntity.getCategory()))
                .collect(Collectors.toList());
    }



    /*
     public  Product save (Product product) {
        return productRepository.save(product);
    }
    */

    public List<Product> getProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }


}
