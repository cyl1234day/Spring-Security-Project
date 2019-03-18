package com.spring.security.securityproject.controller;

import com.spring.security.securityproject.service.validateCode.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chengyl
 * @create 2019-03-16-22:13
 */
@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    @GetMapping("/{type}") //注意：这个请求的 URL 要在配置类中放行
    public void getImage(HttpServletRequest request, HttpServletResponse response, @PathVariable("type") String type) throws Exception {
        ValidateCodeProcessor processor = validateCodeProcessors.get(type + "CodeProcessor");
        if(processor != null) {
            processor.create(request, response);
        }
    }
}
