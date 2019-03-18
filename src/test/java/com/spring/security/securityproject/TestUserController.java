package com.spring.security.securityproject;

import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;

/**
 * @author chengyl
 * @create 2019-03-14-18:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")//请求方式
//                        .param("username", "张三")//请求参数
                        .contentType(MediaType.APPLICATION_JSON_UTF8))//请求头类型
                .andExpect(MockMvcResultMatchers.status().isOk())//响应码
                .andExpect(MockMvcResultMatchers.jsonPath(("$.length()")).value(3));//预期结果
    }


    @Test
    public void testPassword() {
        String pwd = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        for(int i=0; i<10; i++) {
            String encodedPassword = passwordEncoder.encode(pwd);
            System.out.println(encodedPassword);
        }
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Test
    public void test04() {
        System.out.println(securityProperties.getBrowser().getLoginPage());
    }



}
