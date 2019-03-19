package com.spring.security.securityproject.service.validateCode;

import com.spring.security.securityproject.Enum.ValidateCodeType;
import com.spring.security.securityproject.exception.ValidateCodeException;
import com.spring.security.securityproject.pojo.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author chengyl
 * @create 2019-03-18-11:29
 */
public abstract  class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     * 依赖搜索俩个实现 smsCodeGenerator 和 imageCodeGenerator
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;


    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        C code = generate(request);
        save(request, code);
        send(request, response, code);
    }

    /**
     * 校验验证码
     * @param request
     */
    @Override
    public void validate(HttpServletRequest request) {

        //获取当前的类名
//        String prefix = StringUtils.substringBefore(this.getClass().getSimpleName(),"CodeProcessor");
        String sessionName = SESSION_KEY_PREFIX + getValidateCodeType().toString();

        ValidateCode sessionCode = (ValidateCode) request.getSession().getAttribute(sessionName);

        String imageCode = null;
        try {
            //前端输入的验证码
            imageCode = ServletRequestUtils.getRequiredStringParameter(request, getValidateCodeType().getName());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码失败！");
        }
        if(sessionCode == null) {
            //Session中正确的验证码
            throw new ValidateCodeException("验证码不存在！");
        }
        if(StringUtils.isEmpty(imageCode)) {
            throw new ValidateCodeException("验证码为空！");
        }
        if(!sessionCode.isValid()) {
            //要把过期的验证码从Session域中删掉
            request.getSession().removeAttribute(sessionName);
            throw new ValidateCodeException("验证码过期！");
        }
        if(!StringUtils.equalsIgnoreCase(sessionCode.getCode(), imageCode)) {
            throw new ValidateCodeException("验证码错误！");
        }
        //使用过的验证码从Session域中删掉
        request.getSession().removeAttribute(sessionName);
    }


    /**
     * 根据请求的 URI 获取验证类型
     *
     * 可以根据需求来具体变化：需要 SMS 或 IMAGE ，直接 toString
     * 需要 smsCode 或 imageCode， 直接 getName()
     * @return
     */
    private ValidateCodeType getValidateCodeType() {
        //获取当前的类名
        String type = StringUtils.substringBefore(this.getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(StringUtils.upperCase(type));
    }



    /**
     * 生成校验码
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(HttpServletRequest request) {

        String uri = request.getRequestURI();
        if(StringUtils.isEmpty(uri)) {
            return null;
        }
        //根据请求不同，调用不同的 Generator 实现类
        ValidateCodeGenerator generator = null;
//        if(StringUtils.endsWith(uri, "sms")) {
//            generator = validateCodeGenerators.get("smsCodeGenerator");
//        } else {
//            generator = validateCodeGenerators.get("imageCodeGenerator");
//        }
        generator = validateCodeGenerators.get(getValidateCodeType().getName() + "Generator");

        if (generator == null) {
            throw new ValidateCodeException("验证码生成器不存在");
        }
        return (C) generator.generateCode(request);
    }


    /**
     * 把验证码存到 Session 域中
     * @param request
     * @param validateCode
     */
    private void save(HttpServletRequest request, ValidateCode validateCode) {

//        String prefix = StringUtils.substringBefore(this.getClass().getSimpleName(),"CodeProcessor");
        String type = getValidateCodeType().toString();
        String sessionName = SESSION_KEY_PREFIX + StringUtils.upperCase(type);

        request.getSession().setAttribute(sessionName, validateCode);
    }


    /**
     * 发送验证码的方法封装成一个抽象方法留给子类来实现
     * @param request
     */
    protected abstract void send(HttpServletRequest request, HttpServletResponse response, C code) throws Exception;


}
