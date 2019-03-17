package com.spring.security.securityproject.service;

import com.spring.security.securityproject.pojo.ImageCode;
import com.spring.security.securityproject.pojo.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的 验证码接口 实现
 * @author chengyl
 * @create 2019-03-17-13:58
 */
public class MyImageCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ImageCode generateImage(HttpServletRequest request) {
        final String word = "0000111122223333444455556666777788889999" +
                "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

        //使用Spring的工具类获取请求中的参数：ServletRequestUtils.getIntParameter(ServletRequest request, String name, int defaultVal);

        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());


        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200,250));
        g.fillRect(0,0, width, height);
//        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setFont(new Font("Times New Roman", Font.ITALIC, height-3));
        for(int i=0; i<155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x+xl, y+yl);
        }

        StringBuffer sRand = new StringBuffer();
//        for(int i=0; i<4; i++) {
        int length = securityProperties.getCode().getImage().getLength();
        for(int i=0; i<length; i++) {
            char rand = word.charAt(random.nextInt(word.length()));
            sRand.append(rand);
            g.setColor(new Color(20+random.nextInt(110), 20+random.nextInt(110), 20+random.nextInt(110)));
//            g.drawString(rand+"" , 13*i+6, 16);
            g.drawString(rand+"" , (width/length)*i+5, height-5);
        }

        g.dispose();

//        return new ImageCode(image, sRand.toString(), 60);
        return new ImageCode(image, sRand.toString(), securityProperties.getCode().getImage().getExpireTime());
    }


    /**
     * 生成随机背景条纹
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {

        Random random = new Random();
        if(fc > 255) {
            fc = 255;
        }
        if(bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc-fc);
        int g = fc + random.nextInt(bc-fc);
        int b = fc + random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
