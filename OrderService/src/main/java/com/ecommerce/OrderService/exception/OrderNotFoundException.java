package com.ecommerce.OrderService.exception;

import lombok.Data;

@Data
public class OrderNotFoundException extends RuntimeException{


        private String errorCode;

        public OrderNotFoundException(String message, String errorCode){

            super(message);
            this.errorCode= errorCode;

        }

    }

