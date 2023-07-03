package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-06-19 11:20:01
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

