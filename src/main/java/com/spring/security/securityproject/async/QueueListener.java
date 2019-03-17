package com.spring.security.securityproject.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author chengyl
 * @create 2019-03-16-0:29
 * 这个类表示监听整个 Spring容器 的初始化完毕的事件
 *
 */
@Component
@Slf4j
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder holder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread( () -> {
            //通过不断的循环来模拟监听
            while (true) {
                //如果 CompleteOrder 有值了
                if(StringUtils.isNotEmpty(mockQueue.getCompleteOrder())) {
                    //获取订单号
                    String orderId = mockQueue.getCompleteOrder();
                    log.info("返回订单处理结果 " + orderId);
                    //给前端返回值
                    holder.getMap().get(orderId).setResult("订单处理完成 " + orderId);

                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
