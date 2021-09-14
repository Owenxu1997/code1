package com.tjj.bysjerp.sys.mapper;

import com.tjj.bysjerp.sys.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Owen
 * @since 2020-04-07
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据权限或菜单ID删除权限表与角色关系表里的数据
     * @param id
     */
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}
