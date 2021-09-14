package com.tjj.bysjerp.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjj.bysjerp.sys.domain.User;
import com.tjj.bysjerp.sys.mapper.PermissionMapper;
import com.tjj.bysjerp.sys.mapper.RoleMapper;
import com.tjj.bysjerp.sys.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjj.bysjerp.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Owen
 * @since 2020-04-06
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

//    @Resource
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 编写切面需要重写下列方法
     * @param entity
     * @return
     */
    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }


    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户ID删除sys_user_role的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        if (null != ids&&ids.length>0) {
            for (Integer rid : ids) {
                this.roleMapper.insertUserRole(uid,rid);
            }
        }
    }

    /**
     *查询当前用户是否是其他用户的直属领导
     * @param userId
     * @return
     */
    @Override
    public Boolean queryMgrByUserId(Integer userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mgr",userId);
        List<User> users = userMapper.selectList(queryWrapper);
        if (null != users&&users.size()>0) {
            return true;
        }else {
            return false;
        }
    }
}
