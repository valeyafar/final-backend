/*package com.ucc.orders.client;

import com.ucc.orders.model.external.ProductInfoDTO;
import org.springframework.stereotype.Component;

/*@FeignClient(name = "product-service", url = "http://product-service:8080")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductInfoDTO getProductById(@PathVariable("id") Long id);
}*/

/*@Component
public class ProductClient {

    private final WebClientConfig webClient;

    public ProductClient(WebClientConfig.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://product-service:8080/api/products") // ⚠️ Ajustar según tu endpoint
                .build();
    }

    public ProductInfoDTO getProductById(Long id) {
        try {
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductInfoDTO.class)
                    .block(); // modo bloqueante
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al llamar al microservicio de productos: " + e.getMessage(), e);
        }
    }
}*/




