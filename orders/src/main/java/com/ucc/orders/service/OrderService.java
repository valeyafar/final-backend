package com.ucc.orders.service;

import com.ucc.orders.client.ProductClient;
import com.ucc.orders.exceptions.orders.OrderNotExistException;
import com.ucc.orders.exceptions.products.InsufficientStockException;
import com.ucc.orders.exceptions.products.ProductNotFoundException;
import com.ucc.orders.model.dto.OrderDTO;
import com.ucc.orders.model.dto.OrderInfoDTO;
import com.ucc.orders.model.entities.Order;
import com.ucc.orders.model.external.ProductInfoDTO;
import com.ucc.orders.model.mappers.OrdersMappers;
import com.ucc.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrdersMappers ordersMappers;
    //private final RestTemplate restTemplate; //Consultar productos por ID
    private final ProductClient productClient;

    //private final String productServiceUrl = "http://localhost:8080/products";
    //private final String productServiceUrl = "http://localhost:8080/products";



    /*public OrderInfoDTO createOrder(OrderDTO dto) {
        // 1. Consultar producto al microservicio de productos
        ProductInfoDTO product = productClient.getProductById(dto.getProductID());

        // 2. Validar stock
        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }

        // 3. Crear y guardar orden
        Order order = ordersMappers.ordersDTOToEntity(dto);
        Order saved = orderRepository.save(order);

        // 4. Devolver DTO
        return ordersMappers.toDTO(saved);
    }

    private ProductInfoDTO getProductInfo(Long id) {
        try {
            return productClient.getProductById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
    }*/

    /*public OrderInfoDTO createOrder(OrderDTO dto) {
        // 1. Consultar producto al microservicio de productos
        ProductInfoDTO product = getProductById(dto.getProductID());

        // 2. Validar stock
        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }

        // 3. Crear y guardar orden
        Order order = ordersMappers.ordersDTOToEntity(dto);
        Order saved = orderRepository.save(order);

        // 4. Devolver DTO
        return ordersMappers.toDTO(saved);
    }*/
    public OrderInfoDTO createOrder(OrderDTO dto) {
        // 1. Consultar producto al microservicio de productos
        ProductInfoDTO product = getProductById(dto.getProductID());

        // 2. Validar stock
        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }

        // 3. Crear y guardar orden
        Order order = ordersMappers.ordersDTOToEntity(dto);
        Order saved = orderRepository.save(order);

        // 4. Descontar stock (solo si se creó la orden con éxito)
        productClient.discountStock(dto.getProductID(), dto.getQuantity());

        // 5. Devolver DTO
        return ordersMappers.toDTO(saved);
    }


    /*private ProductInfoDTO getProductById(Long id) {
        try {
            return restTemplate.getForObject(productServiceUrl + "/" + id, ProductInfoDTO.class);
        } catch (Exception e) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
    }*/

    private ProductInfoDTO getProductById(Long id) {
        try {
            return productClient.getProductById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
    }





    public List <OrderInfoDTO> getOrdersDTO(){
        return orderRepository.findAll()
                .stream()
                .map(ordersMappers::toDTO)
                .collect(Collectors.toList());
    }

    public OrderInfoDTO getOrderInfoById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe el producto con ID " + id));
        return ordersMappers.toDTO(order);
    }


    /*public OrderInfoDTO updateOrder(Long id, OrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        ProductInfoDTO product = getProductInfo(dto.getProductID());

        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }


        order.setProductID(dto.getProductID());
        order.setQuantity(dto.getQuantity());

        Order updated = orderRepository.save(order);
        return ordersMappers.toDTO(updated);
    }*/

    /*public OrderInfoDTO updateOrder(Long id, OrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        ProductInfoDTO product = getProductById(dto.getProductID());
        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }


        order.setProductID(dto.getProductID());
        order.setQuantity(dto.getQuantity());

        Order updated = orderRepository.save(order);
        return ordersMappers.toDTO(updated);
    }*/

    public OrderInfoDTO updateOrder(Long id, OrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        ProductInfoDTO product = getProductById(dto.getProductID());

        int diferencia = dto.getQuantity() - order.getQuantity(); // nueva - vieja

        // Si la cantidad nueva es mayor, hay que verificar stock adicional
        if (diferencia > 0) {
            if (product.getStock() < diferencia) {
                throw new InsufficientStockException("Stock insuficiente para actualizar la orden. Stock disponible: "
                        + product.getStock() + ", adicional solicitado: " + diferencia);
            }
            productClient.discountStock(dto.getProductID(), diferencia);
        }

        // Si querés devolver stock al reducir la cantidad (opcional)
        else if (diferencia < 0) {
            productClient.increaseStock(dto.getProductID(), -diferencia);


        }

        order.setProductID(dto.getProductID());
        order.setQuantity(dto.getQuantity());

        Order updated = orderRepository.save(order);
        return ordersMappers.toDTO(updated);
    }



    /*public ResponseEntity<Object> deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe el producto con ID " + id));

        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    public ResponseEntity<Object> deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        // Devolver el stock al eliminar la orden
        productClient.increaseStock(order.getProductID(), order.getQuantity());

        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
