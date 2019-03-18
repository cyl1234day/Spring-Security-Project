package com.spring.security.securityproject.service.validateCode.image;

import com.spring.security.securityproject.pojo.ImageCode;
import com.spring.security.securityproject.pojo.ValidateCode;
import com.spring.security.securityproject.service.validateCode.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengyl
 * @create 2019-03-18-12:25
 */
@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ImageCode code) {
        try {
            ImageIO.write(code.getImage(), "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
