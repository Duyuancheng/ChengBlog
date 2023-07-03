package com.dyc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyc.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-06-25 10:48:14
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}

