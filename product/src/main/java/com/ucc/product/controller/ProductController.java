package com.ucc.product.controller;

import com.ucc.product.model.dto.CategoryInfoDTO;
import com.ucc.product.model.dto.ProductInfoDTO;
import com.ucc.product.model.entities.Product;
import com.ucc.product.model.dto.ProductDTO;
import com.ucc.product.service.ProductService;
import jakarta.validation.Valid;
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

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoDTO> getProductsDTO(){
        return productService.getAllInfoProducts();
    }

    //Endpoint para obtener por id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfoDTO getProductById(@PathVariable Long id){
        return productService.getProductInfoById(id);
    }

    /*@GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProductsByPrice(){
        return productService.getProductsByPriceDesc();
    }*/

    @GetMapping("/price")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfoDTO> getProductsByPrice() {
        return productService.getProductsByPriceDesc();
    }


    //Endpoint para crear un objeto usando DTO
    @PostMapping
    public ResponseEntity<Void> newProducts(@Valid @RequestBody ProductDTO product) {
        return productService.newProduct(product);
    }

    //Endpoin para actualizar un producto
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    //Endpoin para eliminar un producto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}/discount-stock")
    public ResponseEntity<Void> discountStock(
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        productService.discountStock(id, quantity);
        return ResponseEntity.ok().build();
    }


}
