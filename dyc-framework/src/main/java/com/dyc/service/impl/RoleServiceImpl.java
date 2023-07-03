package com.dyc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyc.domain.entity.Role;
import com.dyc.mapper.RoleMapper;
import com.dyc.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-06-25 10:48:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return  roleKeys;
        }

        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

