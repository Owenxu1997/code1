package com.tjj.bysjerp.bus.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tjj.bysjerp.bus.domain.Apilist;
import com.tjj.bysjerp.bus.domain.Customer;
import com.tjj.bysjerp.bus.service.ApilistService;
import com.tjj.bysjerp.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 个人API controller
 * @author owenxu
 * @version 1.0
 */
@RestController
@RequestMapping("/personal")
public class PersonalApiController {

    @Autowired
    private ApilistService apilistService;

    /**
     * 全部个人API
     * @param httpSession http会话，用于获得当前登录用户
     * @return 定义格式的json数据
     */
    @RequestMapping("/getPersonalApi")
    public String getPersonalApi(HttpSession httpSession ) {

        User currentUser = (User) httpSession.getAttribute("user");
        List<Apilist> apiList = apilistService.getPersonalApi(currentUser.getId());
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    /**
     * 个人API审核
     * @param httpSession
     * @return
     */
    @RequestMapping("/getPersonalReview")
    public String getPersonalReview(HttpSession httpSession) {

        User currentUser = (User) httpSession.getAttribute("user");
        List<Apilist> apiList = apilistService.getPersonalReview(currentUser.getId());
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    /**
     * 个人已审核API
     * @param httpSession
     * @return
     */
    @RequestMapping("/getPersonalReviewed")
    public String getPersonalReviewed(HttpSession httpSession) {

//        return currentUser.toString();
//        int id = currentUser.getId();
        User currentUser = (User) httpSession.getAttribute("user");
        List<Apilist> apiList = apilistService.getPersonalReviewed(currentUser.getId());
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    /**
     * 个人待测试API
     * @param httpSession
     * @return
     */
    //个人API测试
    @RequestMapping("/getPersonalTest")
    public String getPersonalTest(HttpSession httpSession) {

        User currentUser = (User) httpSession.getAttribute("user");
        List<Apilist> apiList = apilistService.getPersonalTest(currentUser.getId());
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

    /**
     * 个人已测试API
     * @param httpSession
     * @return
     */
    @RequestMapping("/getPersonalTested")
    public String getPersonalTested(HttpSession httpSession) {

        User currentUser = (User) httpSession.getAttribute("user");
        List<Apilist> apiList = apilistService.getPersonalTested(currentUser.getId());
        Gson gson = new Gson();
        String jsonResult = gson.toJson(apiList, new TypeToken<List<Apilist>>(){}.getType());
        //定义返回数据格式使其符合layui表格数据接口规范
        return "{\"code\":0,\"msg\":\"\",\"count\":10,\"data\":" + jsonResult + "}";
    }

}
