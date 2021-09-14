package com.tjj.bysjerp.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.sys.common.Constant;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import com.tjj.bysjerp.sys.common.TreeNode;
import com.tjj.bysjerp.sys.domain.Permission;
import com.tjj.bysjerp.sys.service.PermissionService;
import com.tjj.bysjerp.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * @author owen
     * @date 2020/4/11 16:19
     */
    //---------------------------------------------权限管理开始
    /**
     * 加载权限管理左边的权限树的json
     */
    @RequestMapping("loadPermissionManagerLeftJson")
    public DataGridView loadPermissionManagerLeftJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constant.TYPE_MENU);
        //左边都是菜单只能通过"menu"来查询，所以得指定条件
        List<Permission> list = this.permissionService.list(queryWrapper);

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : list) {
            Boolean spread=permission.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);


    }

    /**
     * 查询
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo) {

        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //只能查询"permission"权限
        queryWrapper.eq("type",Constant.TYPE_PERMISSION);

        //也可以用lambda方式定义,更加便捷参考LambdaQueryWrapper！！！
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPermissioncode()), "permissioncode", permissionVo.getPermissioncode());
        queryWrapper.eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        queryWrapper.orderByAsc("ordernum");

        this.permissionService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constant.TYPE_PERMISSION);
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
    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");

        /*当前代码可行，但数据量大的话运行会比较慢
        List<Permission> list = this.permissionService.list(queryWrapper);*/

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
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo){
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     * @param permissionVo
     * @return
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    //---------------------------------------------权限管理结束
}

