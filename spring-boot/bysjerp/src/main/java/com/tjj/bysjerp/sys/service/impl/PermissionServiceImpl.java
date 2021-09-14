package com.tjj.bysjerp.sys.service.impl;

import com.tjj.bysjerp.sys.domain.Permission;
import com.tjj.bysjerp.sys.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjj.bysjerp.sys.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen
 * @since 2020-04-07
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Override
    public boolean removeById(Serializable id) {

        //根据权限或菜单ID删除权限表与角色关系表里的数据
        PermissionMapper permissionMapper = this.getBaseMapper();
        permissionMapper.deleteRolePermissionByPid(id);


        return super.removeById(id); //删除权限表的数据
    }
}
