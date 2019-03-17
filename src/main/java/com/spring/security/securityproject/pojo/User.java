package com.spring.security.securityproject.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.security.securityproject.annotation.MyValidation;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chengyl
 * @create 2019-03-14-18:25
 */
@Data
public class User {


    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView{}

    @JsonView(UserSimpleView.class)
    private Integer id;

    @MyValidation(message = "用户名自定义注解")
    @JsonView(UserSimpleView.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;
}
