package com.ecommerce.OrderService.exception;

import com.ecommerce.OrderService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionController extends ResponseEntityExceptionHandler {


    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleProductServiceException(OrderNotFoundException exception) {

        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatus.NOT_FOUND);

    }

}
