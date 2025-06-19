package com.ucc.orders.client;

import com.ucc.orders.model.external.ProductInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    // Credenciales para autenticación
    private final String username = "user";
    private final String password = "1234";

    public ProductClient(RestTemplate restTemplate,
                         @Value("${product.service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    // Obtener información completa del producto
    public ProductInfoDTO getProductById(Long productId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = productServiceUrl + "/" + productId;
            ResponseEntity<ProductInfoDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ProductInfoDTO.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el producto con ID " + productId + ": " + e.getMessage());
        }
    }

    // Verificar si el producto existe
    public boolean productExists(Long productId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = productServiceUrl + "/" + productId;
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Void.class
            );

            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    // Descontar el stock del producto
    public void discountStock(Long productId, int quantity) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = productServiceUrl + "/" + productId + "/discount-stock?quantity=" + quantity;
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al descontar stock: " + e.getMessage());
        }
    }

    // Incrementar el stock del producto
    public void increaseStock(Long productId, int quantity) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            String url = productServiceUrl + "/" + productId + "/increase-stock?quantity=" + quantity;
            restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al devolver stock: " + e.getMessage());
        }
    }

}





