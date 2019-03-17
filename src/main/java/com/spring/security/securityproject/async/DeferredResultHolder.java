package com.spring.security.securityproject.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengyl
 * @create 2019-03-16-0:16
 */
@Component
@Data
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> map = new HashMap<>();

}
