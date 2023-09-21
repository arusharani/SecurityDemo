package com.springsecurity.controller;

import com.springsecurity.dto.AuthRequest;
import com.springsecurity.entity.UserInfo;
import com.springsecurity.service.JwtService;
import com.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public String hi(){
        return "hi usha";
    }

    @GetMapping("/name")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String name(){
        return "madhu";
    }

    @GetMapping("/review")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String reviews(){
        return "all items are good";
    }

    @PostMapping("/users")
    public String addUsers(@RequestBody UserInfo userInfo){
       return userService.addUsers(userInfo);

    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());

        }
        else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }



}
