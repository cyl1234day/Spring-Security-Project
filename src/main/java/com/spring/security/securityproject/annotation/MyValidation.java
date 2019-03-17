package com.spring.security.securityproject.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chengyl
 * @create 2019-03-15-11:03
 */
@Constraint(validatedBy = {MyConstraintValidator.class})//表示实现类是哪个
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface MyValidation {

    //以下三个属性一定要有
    String message() default "{javax.validation.constraints.MyValidation.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
