package com.dyc.service.impl;

import com.dyc.domain.ResponseResult;
//import com.dyc.domain.entity.LoginUser;
import com.dyc.domain.entity.LoginUser;
import com.dyc.domain.entity.User;
//import com.dyc.domain.vo.BlogUserLoginVo;
//import com.dyc.domain.vo.UserInfoVo;
import com.dyc.domain.vo.BlogUserLoginVo;
import com.dyc.domain.vo.UserInfoVo;
import com.dyc.service.BlogLoginService;
import com.dyc.utils.BeanCopyUtils;
import com.dyc.utils.JwtUtil;
import com.dyc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    //定义了配置类将authenticationManager注入到ioc容器里面了
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
//        会调用我重新写的UserDetailServiceImpl中的loadUserByUsername方法然后赋值给authenticate
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid生成Token
        LoginUser loginUser=(LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入Redis中
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        //把Token和UserInfo封装返回给前端
//        把user转换成userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo=new BlogUserLoginVo(jwt,userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }


}
