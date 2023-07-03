package com.dyc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyc.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-19 14:57:16
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}