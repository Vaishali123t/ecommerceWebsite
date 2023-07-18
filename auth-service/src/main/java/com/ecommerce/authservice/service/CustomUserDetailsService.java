package com.ecommerce.authservice.service;

import com.ecommerce.authservice.entity.User;
import com.ecommerce.authservice.model.CustomUserDetails;
import com.ecommerce.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userCredentialRepository.findByName(username)
                .orElseThrow(()-> new RuntimeException("User "+ username +" not found"));

        return new CustomUserDetails(user.getName(),user.getPassword());

    }
}
