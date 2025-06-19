package com.ucc.orders.exceptions.orders;

public class OrderNotExistException extends RuntimeException {
    public OrderNotExistException(String message) {
        super(message);
    }
}
