package com.tjj.bysjerp.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Inport;
import com.tjj.bysjerp.bus.domain.Provider;
import com.tjj.bysjerp.bus.service.GoodsService;
import com.tjj.bysjerp.bus.service.InportService;
import com.tjj.bysjerp.bus.service.ProviderService;
import com.tjj.bysjerp.bus.vo.GoodsVo;
import com.tjj.bysjerp.bus.vo.InportVo;
import com.tjj.bysjerp.sys.common.*;
import com.tjj.bysjerp.sys.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Owen
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/inport")
public class InportController {

    @Autowired
    private InportService inportService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 查询
     */
    @RequestMapping("loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo) {

        IPage<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();

        //也可以用lambda方式定义,更加便捷参考LambdaQueryWrapper！！！
        queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0,"providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0,"goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null,"inporttime",inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null,"inporttime",inportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperator()),"operator",inportVo.getOperator());
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()),"remark",inportVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        this.inportService.page(page, queryWrapper);

        List<Inport> records = page.getRecords();
        for (Inport inport : records) {
            Provider provider = this.providerService.getById(inport.getProviderid());
            if (null != provider) {
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.goodsService.getById(inport.getGoodsid());
            if (null!=goods) {
                inport.setGoodsname(goods.getGoodsname());
                inport.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(),records);
    }

    /**
     * @author tjj
     * @date 2020/4/18 23:45
     * 添加
     */
    @RequestMapping("addInport")
    public ResultObj addInport(InportVo inportVo) {
        try {
            inportVo.setInporttime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            inportVo.setOperator(user.getName());
            this.inportService.save(inportVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     */
    @RequestMapping("updateInport")
    public ResultObj updateInport(InportVo inportVo) {
        try {
            this.inportService.updateById(inportVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteInport")
    public ResultObj deleteInport(InportVo inportVo) {
        try {
            this.inportService.removeById(inportVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}

