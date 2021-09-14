package com.tjj.bysjerp.sys.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.sys.common.*;
import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.domain.Permission;
import com.tjj.bysjerp.sys.domain.User;
import com.tjj.bysjerp.sys.service.PermissionService;
import com.tjj.bysjerp.sys.service.RoleService;
import com.tjj.bysjerp.sys.vo.DepartmentVo;
import com.tjj.bysjerp.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  菜单管理前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
        //查询所有菜单列表
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

        //设置只能查菜单
        queryWrapper.eq("type", Constant.TYPE_MENU);
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);

        User user = (User) WebUtils.getSession().getAttribute("user"); //得到登录数据库对应的人员属性
        List<Permission> list = null;
        if (user.getType()==Constant.USER_TYPE_SUPER) {
            list = permissionService.list(queryWrapper);
        }else {
            //根据用户id+角色+权限去查询(user关联role,role关联permission)
            Integer userId = user.getId();
            //根据用户Id查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //根据角色id查询权限和菜单id
            Set<Integer> pids = new HashSet<>();//pid去重
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }

            //根据角色id查询权限
            if (pids.size()>0) {
                queryWrapper.in("id",pids);
                list=permissionService.list(queryWrapper);
            }else {
                list = new ArrayList<>();
            }
        }

        List<TreeNode> treeNodes = new ArrayList<>();
        //根据数据库表单构建集合
        for (Permission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen()==Constant.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
        }
        //构造层级关系——如果pid不是1就不是结点，不必构建
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes,1);
        return new DataGridView(list2);

    }

    /**
     * 菜单管理
     * @author owen
     * @date 2020/4/10 16:51
     */

    //---------------------------------------------菜单管理开始
    /**
     * 加载菜单管理左边的菜单树的json
     */
    @RequestMapping("loadMenuManagerLeftJson")
    public DataGridView loadMenuManagerLeftJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constant.TYPE_MENU);
        //由于只能通过"menu"来查询，所以得指定条件
        List<Permission> list = this.permissionService.list(queryWrapper);

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission menu : list) {
            Boolean spread=menu.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),spread));
        }
        return new DataGridView(treeNodes);


    }

    /**
     * 查询
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo) {

        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
       //先判断id=?pid再判断type
        queryWrapper.eq(permissionVo.getId() != null, "id", permissionVo.getId()).or().eq(permissionVo.getId() != null, "pid", permissionVo.getId());

        //同理只能查询"menu"
        queryWrapper.eq("type",Constant.TYPE_MENU);
        //也可以用lambda方式定义,更加便捷参考LambdaQueryWrapper！！！
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        queryWrapper.orderByAsc("ordernum");

        this.permissionService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constant.TYPE_MENU);
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 自动加载最大排序码
     * @author owen
     * @date 2020/4/10 2:48
     */
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");

        /*当前代码可行，但数据量大的话运行会比较慢
        List<Menu> list = this.permissionService.list(queryWrapper);*/

        IPage<Permission> page = new Page<>(1,1);
        List<Permission> list = this.permissionService.page(page, queryWrapper).getRecords();
        if (list.size()>0) {
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;

    }

    /**
     * 修改
     * @param permissionVo
     * @return
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(PermissionVo permissionVo){
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * 检查删除条件-是否有子节点菜单
     * @param permissionVo
     * @return
     */
    @RequestMapping("checkMenuHasOther")
    public Map<String,Object> checkMenuHasOther(PermissionVo permissionVo) {
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",permissionVo.getId());
        List<Permission> list = this.permissionService.list(queryWrapper);
        if (list.size()>0) {
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }

    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    //---------------------------------------------菜单管理结束


}

