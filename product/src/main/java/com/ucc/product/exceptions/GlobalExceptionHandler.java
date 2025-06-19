package com.ucc.product.exceptions;

import com.ucc.product.exceptions.Category.CategoryNotExistException;
import com.ucc.product.exceptions.Product.ProductNotExistException;
import com.ucc.product.exceptions.dto.ErrorMessageDTO;
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

    //Error producto no existe
    @ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<ErrorMessageDTO> productNotExist(HttpServletRequest request, ProductNotExistException ex){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(999, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.FAILED_DEPENDENCY);
    }


    //Errores de validacion
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

    //Error categoria no encontrada
    @ExceptionHandler(CategoryNotExistException.class)
    public ResponseEntity<ErrorMessageDTO> categoryNotFound(
            CategoryNotExistException ex,
            HttpServletRequest request) {
        ErrorMessageDTO error = new ErrorMessageDTO(404, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }



}