package com.dyc.service;

import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
