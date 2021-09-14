package com.tjj.bysjerp.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.bus.domain.Api;
import com.tjj.bysjerp.bus.service.ApiService;
import com.tjj.bysjerp.bus.vo.ApiVo;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-16
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    /**
     * 查询
     */
    @RequestMapping("loadAllApi")
    public DataGridView loadAllApi(ApiVo apiVo) {

        IPage<Api> page = new Page<>(apiVo.getPage(),apiVo.getLimit());
        QueryWrapper<Api> queryWrapper = new QueryWrapper<>();

        //也可以用lambda方式定义,更加便捷参考LambdaQueryWrapper！！！
        queryWrapper.like(StringUtils.isNotBlank(apiVo.getAddress()),"address",apiVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(apiVo.getType()),"type",apiVo.getType());
        queryWrapper.like(StringUtils.isNotBlank(apiVo.getStatus()),"status",apiVo.getStatus());
        this.apiService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("addApi")
    public ResultObj addApi(ApiVo apiVo) {
        try {
            this.apiService.save(apiVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     */
    @RequestMapping("updateApi")
    public ResultObj updateApi(ApiVo apiVo) {
        try {
            this.apiService.updateById(apiVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteApi")
    public ResultObj deleteApi(Integer id) {
        try {
            this.apiService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    //批量删除
    @RequestMapping("batchDeleteApi")
    public ResultObj batchDeleteApi(ApiVo apiVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            //遍历收集id数据并加入到idList数组中
            for (Integer id : apiVo.getIds()){
                idList.add(id);
            }
            this.apiService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}

