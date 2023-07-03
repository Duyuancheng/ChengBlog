package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-23 10:15:26
 */

public interface UserService extends IService<User> {


    ResponseResult userInfo();
//
    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

