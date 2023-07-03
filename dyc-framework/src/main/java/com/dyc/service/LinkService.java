package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-19 14:57:27
 */
public interface LinkService extends IService<Link> {

     ResponseResult getAllLink();
}

