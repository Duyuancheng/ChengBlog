package com.dyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyc.domain.entity.Tag;
import com.dyc.mapper.TagMapper;
import com.dyc.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-06-24 16:21:03
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

