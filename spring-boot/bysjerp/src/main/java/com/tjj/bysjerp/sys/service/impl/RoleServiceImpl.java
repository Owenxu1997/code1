package com.tjj.bysjerp.sys.service.impl;

import com.tjj.bysjerp.sys.domain.Role;
import com.tjj.bysjerp.sys.mapper.RoleMapper;
import com.tjj.bysjerp.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen
 * @since 2020-04-11
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean removeById(Serializable id) {
        //根据角色id删除sys_permission_role
        this.getBaseMapper().deleteRolePermissionByRid(id);
        //根据角色id删除sys_permission_user
        this.getBaseMapper().deleteRoleUserByRid(id);

        return super.removeById(id);
    }


    /**
     * 根据角色ID查询当前角色拥有的权限或菜单ID
     * @author owen
     * @date 2020/4/12 9:55
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    /**
     * 保存角色和菜单权限之间的关系
     * @author owen
     * @date 2020/4/12 16:14
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        //根据rid删除sys_role_permission
        roleMapper.deleteRolePermissionByRid(rid);

        if (ids!=null&&ids.length>0) {
            for (Integer pid : ids) {
                roleMapper.saveRolePermission(rid,pid);
            }
        }
    }

    /**
     *  查询当前用户拥有的角色ID
     * @param id
     * @return
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }

}
