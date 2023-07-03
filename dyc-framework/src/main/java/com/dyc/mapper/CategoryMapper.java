package com.dyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyc.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-19 11:19:55
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

