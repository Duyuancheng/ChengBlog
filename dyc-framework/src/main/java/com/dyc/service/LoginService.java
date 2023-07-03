package com.dyc.service;

import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.User;

//后台登录系统的service
public interface LoginService {
    ResponseResult login(User user);

}