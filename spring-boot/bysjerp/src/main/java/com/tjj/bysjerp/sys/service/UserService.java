package com.tjj.bysjerp.sys.service;

import com.tjj.bysjerp.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Owen
 * @since 2020-04-06
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户与角色的关系
     * @author owen
     * @date 2020/4/14 14:47
     */
    void saveUserRole(Integer uid, Integer[] ids);

    /**
     * 查询当前用户是否是其他用户的直属领导
     * @param userId
     * @return
     */
    Boolean queryMgrByUserId(Integer userId);
}
