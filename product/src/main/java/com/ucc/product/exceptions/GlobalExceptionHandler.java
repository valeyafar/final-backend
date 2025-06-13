package com.ucc.product.exceptions;

import com.ucc.product.exceptions.Product.ProductNotExistException;
import com.ucc.product.exceptions.dto.ErrorMessageDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotExistException.class)
    public ResponseEntity<ErrorMessageDTO> productNotExist(HttpServletRequest request, ProductNotExistException ex){
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(999, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorMessageDTO, HttpStatus.FAILED_DEPENDENCY);
    }
}