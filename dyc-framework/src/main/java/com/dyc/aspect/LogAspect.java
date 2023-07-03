package com.dyc.aspect;

import com.alibaba.fastjson.JSON;
import com.dyc.annotiation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
//切面类
@Aspect
@Slf4j
public class LogAspect {
    //确定切点
    @Pointcut("@annotation(com.dyc.annotiation.SystemLog)")
    public void pt(){

    }

    //通知方法
    //环绕通知
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint)throws Throwable{
        Object result;
        try {
            //原方法执行前
            handleBefore(joinPoint);
            //原方法
            result = joinPoint.proceed();
            //原方法执行后
            handleAfter(result);
        } finally {
            // 结束后换行
            //最后拼接的System.lineSeparator()是系统的换行符
            log.info("=======End=======" + System.lineSeparator());
        }

        return result;
    }

    private void handleAfter(Object result) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(result));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint){

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog=getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BasinessName   : {}",systemLog.basinessName() );
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod() );
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON(joinPoint.getArgs()));

    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //通过joinPoint.getSignature()获取方法的签名
       MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
//        通过getMethod()方法获取该方法上的@SystemLog注解，最后将该注解返回给调用者
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
