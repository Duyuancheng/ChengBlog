package com.dyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyc.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-06-24 16:21:01
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

