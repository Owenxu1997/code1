package com.tjj.bysjerp.sys.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import com.tjj.bysjerp.sys.common.TreeNode;
import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.service.DepartmentService;
import com.tjj.bysjerp.sys.vo.DepartmentVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-09
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 加载部门管理左边的部门树的json
     */
    @RequestMapping("loadDepartmentManagerLeftJson")
    public DataGridView loadDepartmentManagerLeftJson(DepartmentVo departmentVo){
        List<Department> list = this.departmentService.list();

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Department department : list) {
            Boolean spread=department.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(department.getId(),department.getPid(),department.getTitle(),spread));
        }
        return new DataGridView(treeNodes);


    }

    /**
     * 查询
     */
    @RequestMapping("loadAllDepartment")
    public DataGridView loadAllDepartment(DepartmentVo departmentVo) {

        IPage<Department> page = new Page<>(departmentVo.getPage(),departmentVo.getLimit());
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();

        //也可以用lambda方式定义,更加便捷参考LambdaQueryWrapper！！！
        queryWrapper.like(StringUtils.isNotBlank(departmentVo.getTitle()), "title", departmentVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(departmentVo.getAddress()), "address", departmentVo.getAddress());
        queryWrapper.ge(departmentVo.getRemark() != null, "remark", departmentVo.getRemark());
        queryWrapper.eq(departmentVo.getId() != null, "id", departmentVo.getId()).or().eq(departmentVo.getId() != null, "pid", departmentVo.getId());
        queryWrapper.orderByAsc("ordernum");

        this.departmentService.page(page, queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("addDepartment")
    public ResultObj addDepartment(DepartmentVo departmentVo){
        try {
            departmentVo.setCreatetime(new Date());
            this.departmentService.save(departmentVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 自动加载最大排序码
     * @author tjj
     * @date 2020/4/10 2:48
     */
    @RequestMapping("loadDepartmentMaxOrderNum")
    public Map<String,Object> loadDepartmentMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");

        /*当前代码可行，但数据量大的话运行会比较慢
        List<Department> list = this.departmentService.list(queryWrapper);*/

        IPage<Department> page = new Page<>(1,1);
        List<Department> list = this.departmentService.page(page, queryWrapper).getRecords();
        if (list.size()>0) {
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;

    }

    /**
     * 修改
     * @param departmentVo
     * @return
     */
    @RequestMapping("updateDepartment")
    public ResultObj updateDepartment(DepartmentVo departmentVo){
        try {
            this.departmentService.updateById(departmentVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * 检查删除条件-是否有子节点菜单
     * @param departmentVo
     * @return
     */
    @RequestMapping("checkDepartmentHasOther")
    public Map<String,Object> checkDepartmentHasOther(DepartmentVo departmentVo) {
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",departmentVo.getId());
        List<Department> list = this.departmentService.list(queryWrapper);
        if (list.size()>0) {
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }

    /**
     * 删除
     * @param departmentVo
     * @return
     */
    @RequestMapping("deleteDepartment")
    public ResultObj deleteDepartment(DepartmentVo departmentVo){
        try {
            this.departmentService.removeById(departmentVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

}

