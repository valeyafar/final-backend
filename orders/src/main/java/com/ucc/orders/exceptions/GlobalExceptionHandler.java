package com.ucc.orders.exceptions;

import com.ucc.orders.exceptions.dto.ErrorMessageDTO;
import com.ucc.orders.exceptions.orders.OrderNotExistException;
import com.ucc.orders.exceptions.products.InsufficientStockException;
import com.ucc.orders.exceptions.products.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotExistException.class)
    public ResponseEntity<ErrorMessageDTO> categoryNotFound(
            OrderNotExistException ex,
            HttpServletRequest request) {
        ErrorMessageDTO error = new ErrorMessageDTO(404, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = Optional.ofNullable(ex.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse("Error de validación");


        ErrorMessageDTO error = new ErrorMessageDTO(
                400,
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorMessageDTO error = new ErrorMessageDTO(
                500,
                "Error interno del servidor",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleProductNotFoundException(
            ProductNotFoundException ex,
            HttpServletRequest request) {
        ErrorMessageDTO error = new ErrorMessageDTO(
                404,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorMessageDTO> handleInsufficientStockException(
            InsufficientStockException ex,
            HttpServletRequest request
    ) {
        ErrorMessageDTO error = new ErrorMessageDTO(
                400,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
