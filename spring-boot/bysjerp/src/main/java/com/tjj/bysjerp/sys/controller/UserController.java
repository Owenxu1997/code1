package com.tjj.bysjerp.sys.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.sys.common.*;
import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.domain.Role;
import com.tjj.bysjerp.sys.domain.User;
import com.tjj.bysjerp.sys.service.DepartmentService;
import com.tjj.bysjerp.sys.service.RoleService;
import com.tjj.bysjerp.sys.service.UserService;
import com.tjj.bysjerp.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;
    /**
     * 用户全查询
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo) {
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()),"loginname",userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        queryWrapper.eq("type", Constant.USER_TYPE_NORMAL); //查询系统用户
        queryWrapper.eq(userVo.getDepartmentid()!=null,"departmentid",userVo.getDepartmentid());
        this.userService.page(page, queryWrapper);

        List<User> list = page.getRecords();
        for (User user : list) {
            Integer departmentid = user.getDepartmentid();
            if (departmentid!=null) {
                //getById会先从缓存中取再去数据库取，因为调用时被CacheAspect切了
                Department one = departmentService.getById(departmentid);
                user.setDepartmentname(one.getTitle());
            }
            Integer mgr = user.getMgr();
            if (mgr!=null) {
                User one = this.userService.getById(mgr);
                user.setLeadername(one.getName());
            }
        }

        return new DataGridView(page.getTotal(),list);
    }


    /**
     * 自动加载最大排序码
     * @author owen
     * @date 2020/4/13 20:47
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");

        /*当前代码可行，但数据量大的话运行会比较慢
        List<User> list = this.userService.list(queryWrapper);*/

        IPage<User> page = new Page<>(1,1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if (list.size()>0) {
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;

    }

    /**
     * 根据部门ID查询用户
     * @author owen
     * @date 2020/4/13 21:11
     */
    @RequestMapping("loadUserByDepartmentId")
    public DataGridView loadUserByDepartmentId (Integer departmentid) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(departmentid != null,"departmentid",departmentid);
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constant.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * 把用户名转成拼音显示在登录输入框
     */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username) {
        Map<String,Object> map  = new HashMap<>();

        if (null != username) {
            map.put("value",PinYinUtils.ToPinyin(username));
        }else {
            map.put("value","");
        }
        return map;
    }

    /**
     * @author owen
     * @date 2020/4/13 23:56
     * 添加用户
     */
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo) {
        try {
            userVo.setType(Constant.USER_TYPE_NORMAL);//设置类型
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);//设置盐
//            userVo.setPassword(new MD5(salt,2));
            userVo.setPassword(new Md5Hash(Constant.USER_DEFAULT_PASSWORD,salt,2).toString());
            this.userService.save(userVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.ADD_ERROR;
        }
    }

    /**
     * 根据用户Id查询一个用户
     * @author owen
     * @date 2020/4/14 1:40
     */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id) {
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 修改用户
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo userVo) {
        try {
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除用户
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.DISPATCH_ERROR;
        }
    }
    /**
     *根据当前用户Id查询是否是其他用户的直属领导
     */
    @RequestMapping("queryMgrByUserId")
    public ResultObj queryMgrByUserId(Integer userId) {
        Boolean isMgr = userService.queryMgrByUserId(userId);
        if (isMgr) {
            return ResultObj.DELETE_ERROR_NEWS;
        } else {
            return ResultObj.DELETE_QUERY;
        }
    }


    /**
     * 重置密码
     */
    @RequestMapping("resetPassword")
    public ResultObj resetPassword(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            user.setPassword(new Md5Hash(Constant.USER_DEFAULT_PASSWORD,salt,2).toString());//重设密码
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultObj.RESET_ERROR;
        }
    }

    /**
     * 根据用户ID查询角色中已拥有的角色
     */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id) {
        //1.查询所有可用的角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);

        //查询当前用户拥有的角色ID
        List<Integer> currentUserRoleIds = this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            //LAY_CHECKED为layui下复选框选中属性
            Boolean LAY_CHECKED=false;
            Integer roleId = (Integer) map.get("id");
                for (Integer rid : currentUserRoleIds) {
                    if (rid==roleId) {
                        LAY_CHECKED=true;
                        break;
                    }
                }
                map.put("LAY_CHECKED",LAY_CHECKED);
            }
        return new DataGridView((long) listMaps.size(),listMaps);
    }

    /**
     * 保存用户和角色之间的关系
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids) {
        try {
            this.userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }


    /**
     *
     * @param oldPassword 原密码
     * @param newPasswordOne 第一次输入密码
     * @param newPasswordTwo 第二次输入确认密码
     * @return
     */
    @RequestMapping("changePassword")
    public ResultObj changePassword(String oldPassword,String newPasswordOne,String newPasswordTwo) {
        //1.通过session获取当前用户ID
        User user = (User) WebUtils.getSession().getAttribute("user");
        /**
         * 2.将oldPassword加salt并散列迭代两次再和数据库中密码进行对比
         */
        Integer userId = user.getId();
        User user1 = userService.getById(userId);
        //2-1获取当前用户的salt
        String salt = user1.getSalt();
        //2-2通过用户输入密码，从数据苦中查询相对应的salt,散列迭代生成新的旧密码
        String oldPassword1 = new Md5Hash(oldPassword,salt,Constant.HASH_ITERATIONS).toString();
        if (oldPassword1.equals(user1.getPassword())) {
            if (newPasswordOne.equals(newPasswordTwo)) {
                //3.生成新的密码
                String newPassword = new Md5Hash(newPasswordOne,salt,Constant.HASH_ITERATIONS).toString();
                user1.setPassword(newPassword);
                userService.updateById(user1);
                return ResultObj.UPDATE_SUCCESS;
            }else {
                return ResultObj.UPDATE_ERROR;
            }
        }else {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 返回当前已登录的用户
     */
    @RequestMapping("getNowUser")
    public User getNowUser() {
        //1.获取当前session中的user
        User user  = (User) WebUtils.getSession().getAttribute("user");
        System.out.println("*********************");
        System.out.println(user);
        return user;
    }

    /**
     * 修改用户头像
     * （参考GoodsController）
     */
    @RequestMapping("updateUserInfo")
    public ResultObj updateUserInfo(UserVo userVo) {
        try {
            if (!(userVo.getImgpath()!=null&&userVo.getImgpath().equals(Constant.IMAGES_DEFAULT_PNG))) {
                if (userVo.getImgpath()!=null&&userVo.getImgpath().endsWith("_temp")) {
                    String newName = AppFileUtils.renameFile(userVo.getImgpath());
                    userVo.setImgpath(newName);
                    //删除原图
                    String oldPath = userService.getById(userVo.getId()).getImgpath();
                    AppFileUtils.removeFileByPath(oldPath);
                }
            }
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}

