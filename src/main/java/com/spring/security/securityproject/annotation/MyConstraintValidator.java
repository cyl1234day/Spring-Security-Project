package com.spring.security.securityproject.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author chengyl
 * @create 2019-03-15-11:06
 */
//不需要加 @Component 之类的注解
public class MyConstraintValidator implements ConstraintValidator<MyValidation, String> {

    @Override
    public void initialize(MyValidation constraintAnnotation) {
        //初始化方法
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //value为注解标注的属性的值
        System.out.println(value);
        if(StringUtils.isEmpty(value)) {
            return false;
        }
        return true;
    }
}
