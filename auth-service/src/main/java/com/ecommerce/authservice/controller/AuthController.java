package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.entity.User;
import com.ecommerce.authservice.model.AuthRequest;
import com.ecommerce.authservice.model.UserRequest;
import com.ecommerce.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest){

        String user= authService.saveUser(userRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }
    @GetMapping("/getToken")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest){

        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));

        if(authenticate.isAuthenticated()){
            String token= authService.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        else{
            throw new RuntimeException("invalid access");
        }

    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validateToken(@PathVariable String token){
        authService.validateToken(token);
        return new ResponseEntity<>("Token is valid",HttpStatus.OK);
    }

}
