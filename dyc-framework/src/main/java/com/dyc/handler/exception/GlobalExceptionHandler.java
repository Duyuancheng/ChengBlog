package com.dyc.handler.exception;

import com.dyc.domain.ResponseResult;
import com.dyc.enums.AppHttpCodeEnum;
import com.dyc.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//它是Spring框架提供的一个异常处理机制，可以用于捕获全局Controller层抛出的异常
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    它被用于定义Controller中的异常处理方法，用于处理指定类型的异常。当Controller方法抛出指定类型的异常时，Spring就会自动调用相应的异常处理方法进行处理
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常!{}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常!{}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
