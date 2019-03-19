package com.spring.security.securityproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.security.securityproject.pojo.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
    public String asdlg() {
        return "hello";
    }


    @GetMapping("/ok")
    public String asdlg222() {
        return "<h1>OK</h1>";
    }

}
