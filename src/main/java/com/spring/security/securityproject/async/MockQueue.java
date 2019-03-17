package com.spring.security.securityproject.async;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chengyl
 * @create 2019-03-15-23:50
 */
@Component
@Data
@Slf4j
public class MockQueue {

    //下单
    private String acceptOrder;

    //订单完成
    private String completeOrder;


    public void setAcceptOrder(String acceptOrder) throws Exception {
        new Thread( () -> {
            log.info("接到下单请求 " + acceptOrder);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 两秒钟后给 completeOrder 属性赋值
             * 在其他类中监听该属性是否有值，如果有值了，
             * 表示订单完成，继续后续的操作
             */
            this.completeOrder = acceptOrder;
            log.info("订单完成 " + acceptOrder);
            }
        ).start();

    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
