package com.tjj.bysjerp.sys.mapper;

import com.tjj.bysjerp.sys.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2020-04-11
 */
//@Component(value = "roleMapper")
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据角色id删除sys_role_permission
     * @author owen
     * @date 2020/4/11 22:11
     */
    void deleteRolePermissionByRid(Serializable id);

    /**
     * 根据角色id删除sys_role_user
     * @author owen
     * @date 2020/4/11 22:11
     */
    void deleteRoleUserByRid(Serializable id);

    /**
     * 根据角色ID查询当前角色拥有的权限或菜单ID
     * @author owen
     * @date 2020/4/12 10:03
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * 保存角色和菜单权限之间的关系
     * @author owen
     * @date 2020/4/12 16:09
     */
    //多个参数需要加注解@Param
    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    /**
     * 根据用户ID删除用户角色中间表的数据
     * @param id
     */
    void deleteRoleUserByUid(Serializable id);

    /**
     * 查询当前用户拥有的角色ID
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);

    /**
     * 保存角色和用户的关系
     * @param uid
     * @param rid
     */
    void insertUserRole(@Param("uid")Integer uid, @Param("rid")Integer rid);
}
