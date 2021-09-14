package com.tjj.bysjerp.sys.service;

import com.tjj.bysjerp.sys.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;
import sun.java2d.pipe.AAShapePipe;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Owen
 * @since 2020-04-11
 */
public interface RoleService extends IService<Role> {


    /**
     * 根据角色ID查询当前角色拥有的权限或菜单ID
     * @author owen
     * @date 2020/4/12 10:01
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     *保存角色和菜单权限的关系
     * @author owen
     * @date 2020/4/12 16:14
     */
    void saveRolePermission(Integer roleId, Integer[] ids);

    /** 查询当前用户拥有的角色ID
     * @author owen
     * @date 2020/4/14 14:08
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
