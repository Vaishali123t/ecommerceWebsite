package com.ecommerce.authservice.service;

import com.ecommerce.authservice.entity.User;

public interface UserService {

    User getUserDetailsByUserName(String username);

}
