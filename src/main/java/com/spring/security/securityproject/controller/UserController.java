package com.spring.security.securityproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.security.securityproject.pojo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author chengyl
 * @create 2019-03-14-18:28
 */
@RestController
public class UserController {

    @PostMapping(value = "/user/{id}")
    @JsonView(User.UserDetailView.class)
    public User createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           bindingResult.getAllErrors().stream().forEach((error) -> {
               System.out.println(error.getDefaultMessage());
           });
        }
        System.out.println(user);
        user.setId(3);
        return user;
    }

    @GetMapping("/hello")
    public String asdlg(HttpServletRequest request) {
        System.out.println("Session timeout:  " + request.getSession().getMaxInactiveInterval());
        return "hello";
    }

//    @GetMapping(SecurityConstants.DEFAULT_SESSION_INVALID_URL)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public LoginResponse sessionInvalid() {
//        return new LoginResponse("Session Invalid");
//    }


    @GetMapping("/ok")
    public String asdlg222() {
        return "<h1>OK</h1>";
    }


    @GetMapping("/me")
    public UserDetails getMe(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        return user;
    }

}
