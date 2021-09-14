package com.tjj.bysjerp.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Owen on 2020/4/15 0:42
 */
@Controller
@RequestMapping("/bus")
public class BusinessController {

    /**
     * @author owen xu
     * 跳转至个人所有API
     * @return 个人所有API页面
     */
    //个人所有api
    @RequestMapping("personalAll")
    public String toPersonalAll() {
        return "business/api/personalAll";
    }

    /**
     * @author owen xu
     * 跳转至个人待审核API
     * @return
     */
    //个人待审核API
    @RequestMapping("personalReview")
    public String toPersonalReview() {
        return "business/api/personalReview";
    }

    /**
     * @author owen xu
     * 跳转至个人已审核API页面
     * @return
     */
    @RequestMapping("personalReviewed")
    public String personalReviewed() {
        return "business/api/personalReviewed";
    }

    /**
     * @author owen xu
     * 跳转至飞人待测试API页面
     * @return
     */
    //个人待测试API
    @RequestMapping("personalTest")
    public String personalTest() {
        return "business/api/personalTest";
    }

    /**
     * @author owen xu
     * 跳转至个人已测试API页面
     * @return
     */
    @RequestMapping("personalTested")
    public String personalTested() {
        return "business/api/personalTested";
    }

    /**
     * @author owen xu
     * 管理员API审核页面
     * @return
     */
    @RequestMapping("review")
    public String review() {
        return "business/api/review";
    }

    /**
     * @author owen xu
     * 管理员API测试页面
     * @return
     */
    @RequestMapping("test")
    public String test() {
        return "business/api/test";
    }

    /**
     *
     * @return
     */
    @RequestMapping("addApi")
    public String toAdd() {
        return "business/api/addApi";
    }

    /**
     * @author tjj
     * @date 2020/4/15 0:43
     * 跳转到客户管理
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "business/customer/customerManager";
    }


    /**
     * @author tjj
     * @date 2020/4/16 17:29
     * 跳转到API管理
     */
    @RequestMapping("toApiManager")
    public String toApiManager() {
        return "business/api/apiManager";
    }


    /**
     * @author tjj
     * @date 2020/4/16 17:29
     * 跳转到供应商管理
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager() {
        return "business/provider/providerManager";
    }

    /**
     * @author tjj
     * @date 2020/4/16 23:48 
     * 跳转到商品管理界面
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager() {
        return "business/goods/goodsManager";
    }

    /**
     * @author tjj
     * @date 2020/4/18 18:07
     * 跳转到进货管理界面
     */
    @RequestMapping("toInportManager")
    public String toInportManager() {
        return "business/inport/inportManager";
    }

    /**
     * @author tjj
     * @date 2020/4/18 18:07
     * 跳转到退货查询管理界面
     */
    @RequestMapping("toOutportManager")
    public String toOutportManager() {
        return "business/outport/outportManager";
    }

   /**
    * 跳转到商品销售管理页面
    * @author tjj
    * @date 2020/4/20 16:45
    */
    @RequestMapping("toSalesManager")
    public String toSalesManager(){
        return "business/sales/salesManager";
    }


}
