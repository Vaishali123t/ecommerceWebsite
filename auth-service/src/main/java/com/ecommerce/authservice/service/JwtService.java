package com.ecommerce.authservice.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService{

    void validateToken(final String token);

    String generateToken(String userName);

}
