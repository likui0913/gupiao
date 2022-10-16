package com.gupiao.aop;

import com.gupiao.api.response.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Order(1)
@Aspect
public class WebLoadAop {

    public static Map<String,Long> cache = new HashMap<>();

    @Pointcut(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable{

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();

        String token = request.getSession().getId();
        if(!cache.containsKey(token)){
            ResponseBean bean = new ResponseBean();
            bean.setStatus(Boolean.FALSE);
            bean.setMsg("未登录");
            return bean;
        }else{
            return joinPoint.proceed(joinPoint.getArgs());
        }

    }

}
