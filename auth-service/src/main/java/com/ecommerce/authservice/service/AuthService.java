package com.ecommerce.authservice.service;

import com.ecommerce.authservice.model.UserRequest;

public interface AuthService {

    public String saveUser(UserRequest user);

    String generateToken(String username);

    void validateToken(String token);

}
