package com.ucc.product.controller;


import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Endpoint para obtener todos los productos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProduct() {
        return productService.getAllProducts();
    }

    //Endpoint para obtener por id
    /*@GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }*/

    //Endpoint para obtener por id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    //Endpoin para crear un producto
    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        this.productService.createProduct(product);
    }*/

    //Endpoint para crear un objeto usando DTO
    @PostMapping
    public ResponseEntity<Void> newProducts(@RequestBody ProductDTO product) {
        return productService.newProduct(product);
    }

    //Endpoin para actualizar un producto
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    //Endpoin para eliminar un producto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoDTO> getProductsDTO(){
        return productService.getAllInfoProducts();
    }

    @GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByPrice(){
        return productService.getProductsByPriceDesc();
    }



}
