package com.tjj.bysjerp.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjj.bysjerp.bus.domain.*;
import com.tjj.bysjerp.bus.service.CustomerService;
import com.tjj.bysjerp.bus.service.GoodsService;
import com.tjj.bysjerp.bus.service.SalesService;
import com.tjj.bysjerp.bus.vo.InportVo;
import com.tjj.bysjerp.bus.vo.ProviderVo;
import com.tjj.bysjerp.bus.vo.SalesVo;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import com.tjj.bysjerp.sys.common.WebUtils;
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
 * @since 2020-04-20
 */
@RestController
@RequestMapping("/sales")
public class SalesController {
    
    @Autowired
    private SalesService salesService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private GoodsService goodsService;


    /**
     * 查询所有商品销售信息
     */
    @RequestMapping("loadAllSales")
    public DataGridView loadAllSales(SalesVo salesVo) {

        IPage<Sales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());
        QueryWrapper<Sales> queryWrapper = new QueryWrapper<>();

        //根据客户进行查询
        queryWrapper.eq(salesVo.getCustomerid()!=null&&salesVo.getCustomerid()!=0,"customerid",salesVo.getCustomerid());
        //根据商品查询
        queryWrapper.eq(salesVo.getGoodsid()!=null&&salesVo.getGoodsid()!=0,"goodsid",salesVo.getGoodsid());
        //根据时间查询
        queryWrapper.ge(salesVo.getStartTime()!=null,"salestime",salesVo.getStartTime());
        queryWrapper.le(salesVo.getEndTime()!=null,"salestime",salesVo.getEndTime());
        this.salesService.page(page, queryWrapper);

        List<Sales> records = page.getRecords();
        for (Sales sales : records) {
            //设置客户姓名
            Customer customer = this.customerService.getById(sales.getCustomerid());
            if (null!=customer) {
                sales.setCustomername(customer.getCustomername());
            }
            //设置商品名称
            Goods goods = this.goodsService.getById(sales.getGoodsid());
            if (null!=goods) {
                //设置商品名（API）
                sales.setGoodsname(goods.getGoodsname());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    
    /**
     * @author tjj
     * @date 2020/4/20 14:26 
     * 添加商品销售
     */
    @RequestMapping("addSales")
    public ResultObj addSales(SalesVo salesVo) {
        try {
            salesVo.setSalestime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            salesVo.setOperator(user.getName());
            this.salesService.save(salesVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改
     */
    @RequestMapping("updateSales")
    public ResultObj updateSales(SalesVo salesVo) {
        try {
            this.salesService.updateById(salesVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteSales")
    public ResultObj deleteSales(SalesVo salesVo) {
        try {
            this.salesService.removeById(salesVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

