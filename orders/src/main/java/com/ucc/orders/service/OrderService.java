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
    private final ProductClient productClient;


    //Metodo para crear orden y consultar al microservico de productos
    public OrderInfoDTO createOrder(OrderDTO dto) {

        ProductInfoDTO product = getProductById(dto.getProductID());

        if (product.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Producto sin stock suficiente. Stock disponible: "
                    + product.getStock() + ", solicitado: " + dto.getQuantity());
        }

        Order order = ordersMappers.ordersDTOToEntity(dto);
        Order saved = orderRepository.save(order);

        productClient.discountStock(dto.getProductID(), dto.getQuantity());

        return ordersMappers.toDTO(saved);
    }

    //Metodo para obtener productos
    private ProductInfoDTO getProductById(Long id) {
        try {
            return productClient.getProductById(id);
        } catch (Exception e) {
            throw new ProductNotFoundException("Producto con ID " + id + " no encontrado");
        }
    }

    //Metodo para obtener ordenes
    public List <OrderInfoDTO> getOrdersDTO(){
        return orderRepository.findAll()
                .stream()
                .map(ordersMappers::toDTO)
                .collect(Collectors.toList());
    }

    //Metodo para obtener ordenes por id
    public OrderInfoDTO getOrderInfoById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe el producto con ID " + id));
        return ordersMappers.toDTO(order);
    }


    //Metodo para actualizar ordenes
    public OrderInfoDTO updateOrder(Long id, OrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        ProductInfoDTO product = getProductById(dto.getProductID());

        int diferencia = dto.getQuantity() - order.getQuantity(); // nueva - vieja

        // verificar stock
        if (diferencia > 0) {
            if (product.getStock() < diferencia) {
                throw new InsufficientStockException("Stock insuficiente para actualizar la orden. Stock disponible: "
                        + product.getStock() + ", adicional solicitado: " + diferencia);
            }
            productClient.discountStock(dto.getProductID(), diferencia);
        }

        else if (diferencia < 0) {
            productClient.increaseStock(dto.getProductID(), -diferencia);


        }

        order.setProductID(dto.getProductID());
        order.setQuantity(dto.getQuantity());

        Order updated = orderRepository.save(order);
        return ordersMappers.toDTO(updated);
    }



    //Metodo para obtener eliminar ordenes
    public ResponseEntity<Object> deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotExistException("No existe la orden con ID " + id));

        // Devolver el stock al eliminar la orden
        productClient.increaseStock(order.getProductID(), order.getQuantity());

        orderRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
