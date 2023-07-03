package com.dyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyc.constants.SystemConstants;
import com.dyc.domain.ResponseResult;
import com.dyc.domain.entity.Link;
import com.dyc.domain.vo.LinkVo;
import com.dyc.mapper.LinkMapper;
import com.dyc.service.LinkService;
import com.dyc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-19 14:57:27
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper,Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);

        List<Link> links = list(queryWrapper);
        //转换成VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }
}
