package com.dyc.handler.security;

import com.alibaba.fastjson.JSON;
import com.dyc.domain.ResponseResult;
import com.dyc.enums.AppHttpCodeEnum;
import com.dyc.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//自定义的Spring Security认证失败处理器
//当用户访问需要授权的资源时，如果其权限不足，则会抛出AccessDeniedException异常，
// 此时Spring Security会调用实现了AccessDeniedHandler接口的自定义异常处理器，该处理器中的handle方法可以根据自定义的需求进行异常处理
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
