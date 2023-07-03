package com.dyc.handler.security;

import com.alibaba.fastjson.JSON;
import com.dyc.domain.ResponseResult;
import com.dyc.enums.AppHttpCodeEnum;
import com.dyc.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 针对认证的异常处理类
 * 这个类是自定义的Spring Security认证处理器(AuthenticationEntryPoint接口的实现类)，主要用于处理用户请求资源需要进行认证，但用户尚未登录的情况。
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result=null;
        if(e instanceof BadCredentialsException){
             result= ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            result =ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result=ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));

    }
}
