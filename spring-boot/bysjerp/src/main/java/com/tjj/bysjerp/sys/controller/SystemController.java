package com.tjj.bysjerp.sys.controller;

import com.tjj.bysjerp.sys.common.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Owen on 2020/4/6 23:23
 * Contributer Owen Xu
 */

@Controller
@RequestMapping("sys")
public class SystemController {

    /**
     * 实现跳转到登录页面
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "system/index/login";
    }

    /**
     * 实现跳转到主页（首页）
     */
    @RequestMapping("index")
    public String index() {
        return "system/index/index";
    }

    /**
     * 实现跳转到工作台页面
     */
    @RequestMapping("toWorkBench")
    public String toWorkBench() {
        return "system/index/WorkBench";
    }


    /**
     * 跳转到个人资料页面
     * @return
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo(){
        return "system/user/userInfo";
    }
    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("toChangePassword")
    public String toChangePassword(){
        return "system/user/changePassword";
    }
    /**
     * 实现退出跳转到登录页面
     */
    @RequestMapping("toSignOut")
    public String toSignOut() {
        WebUtils.getSession().removeAttribute("user");
        return "system/index/login";
    }

    /**
     * 实现跳转到日志管理页面
     */
    @RequestMapping("tologinfoManager")
    public String tologinfoManager() {
        return "system/loginfo/loginfoManager";
    }

    /**
     * 实现跳转到公告管理页面
     */
    @RequestMapping("toNoticeManager")
    public String toNoticeManager() {
        return "system/notice/noticeManager";
    }

    /**
     *实现跳转到部门管理的相关跳转
     */
    @RequestMapping("toDepartmentManager")
    public String toDepartmentManager() {
        return "system/department/departmentManager";
    }
    @RequestMapping("toDepartmentLeft")
    public String toDepartmentLeft() {
        return "system/department/departmentLeft";
    }
    @RequestMapping("toDepartmentRight")
    public String toDepartmentRight() {
        return "system/department/departmentRight";
    }

    /**
     *实现跳转到菜单管理的相关跳转
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }
    @RequestMapping("toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/menuLeft";
    }
    @RequestMapping("toMenuRight")
    public String toMenuRight() {
        return "system/menu/menuRight";
    }

    /**
     *实现跳转到权限管理的相关跳转
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/permissionLeft";
    }
    @RequestMapping("toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/permissionRight";
    }

    /**
     * 实现跳转到角色管理页面
     */
    @RequestMapping("toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManager";
    }

    /**
     * 实现跳转到用户管理页面
     */
    @RequestMapping("toUserManager")
    public String toUserManager() {
        return "system/user/userManager";
    }

    /**
     * 跳转到缓存管理页面
     */
    @RequestMapping("toCacheManager")
    public String toCacheManager() {
        return "system/cache/cacheManager";
    }



}
