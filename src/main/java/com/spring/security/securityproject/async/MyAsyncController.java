package com.spring.security.securityproject.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * @author chengyl
 * @create 2019-03-15-23:48
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class MyAsyncController {

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder holder;


    @GetMapping
    public Callable<String> handleAsync() {

        log.info("主线程开始");

        Callable<String> result = () -> {
            log.info("子线程开始");
            Thread.sleep(5000);
            log.info("子线程结束");
            return "success";
        };

        log.info("主线程结束");
        return result;
    }



    @GetMapping("/deffer")
    public DeferredResult<String> handleAsync2() throws Exception {

        log.info("主线程开始");
        //生成订单号
        String orderId = RandomStringUtils.randomNumeric(16);

        //处理订单
        mockQueue.setAcceptOrder(orderId);

        DeferredResult<String> result = new DeferredResult<>();
        holder.getMap().put(orderId, result);


        log.info("主线程结束");
        return result;
    }

}
