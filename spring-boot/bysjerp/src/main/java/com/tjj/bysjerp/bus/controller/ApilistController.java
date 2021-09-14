package com.tjj.bysjerp.bus.controller;


import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;
import com.tjj.bysjerp.bus.domain.Api;
import com.tjj.bysjerp.bus.domain.Apilist;
import com.tjj.bysjerp.bus.service.ApiTestService;
import com.tjj.bysjerp.bus.service.ApilistService;
import com.tjj.bysjerp.bus.utils.ApiTestUtil;
import com.tjj.bysjerp.bus.vo.ResultVO;
import com.tjj.bysjerp.sys.common.AppFileUtils;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import com.tjj.bysjerp.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 *  API前端控制器
 *  其中getAllApi面向所有用户
 *  API测试和API审核面向管理员
 *
 * @author Owen Xu
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/apilist")
public class ApilistController {

    @Autowired
    ApilistService apilistService;


    /**
     * get all api
     * @return
     */
    @RequestMapping("/getAllApi")
    public String getAllApi() {

        List<Apilist> apiList = apilistService.getAllApiReviewed();
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    //添加api
    //springmvc自动封装：将请求参数和参数绑定，要求请求参数名和pojo里的一样
    @PostMapping("/addApi")
    public String addApi(Apilist api, HttpSession httpSession) {
        User currentUser = (User) httpSession.getAttribute("user");
        api.setUid(currentUser.getId());
        apilistService.addApi(api);
        return "";
    }

    /**
     * 全局API测试
     * @return
     */
    @RequestMapping("/getTest")
    public String getTest() {
        List<Apilist> apiList = apilistService.getTest();
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Api>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    /**
     * 全局API审核
     * @return
     */
    @RequestMapping("/getReview")
    public String getReview() {

        List<Apilist> apiList = apilistService.getReview();
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Api>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
        //return new DataGridView(apiList);
    }

    /**
     * set api review passed
     * @param apiId api id
     * @return success or error
     */
    @RequestMapping("/reviewPassed")
    public ResultObj reviewPassed(Integer apiId){
        try {
            this.apilistService.reviewPassed(apiId);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * set api test passed
     * @param apiId api id
     * @return success or error
     */
    @RequestMapping("/testPassed")
    public ResultObj testPassed(Integer apiId){
        try {
            this.apilistService.testPassed(apiId);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * delete api by Id
     * @param apiId api id
     * @return success or error
     * @author owenxu
     */
    @RequestMapping("/deleteById")
    public ResultObj deleteById(Integer apiId){
        try {
            this.apilistService.deleteById(apiId);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

//    @RequestMapping()
}

