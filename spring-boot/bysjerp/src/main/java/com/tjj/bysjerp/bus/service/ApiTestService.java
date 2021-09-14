package com.tjj.bysjerp.bus.service;

import com.alibaba.fastjson.JSON;
import com.tjj.bysjerp.bus.utils.ApiTestUtil;
import com.tjj.bysjerp.bus.vo.ResultVO;

import com.tjj.bysjerp.sys.domain.User;
import org.omg.CORBA.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ApiTestService {

    ApiTestUtil apiTestUtil;

    public String getParams(String username, String password) {
        String url = "localhost:8080/login/login";
        MultiValueMap<String, String > params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        ResultVO resultVO = apiTestUtil.PostRequest(url, params);

        LinkedHashMap infoMap = (LinkedHashMap) resultVO.getData();
        return resultVO.toString();
    }


    /**
     * 向API地址发送Get请求，并打印
     * @param url
     * @param params
     * @return
     */
//    public String sendGet(String url, MultiValueMap<String, String> params) {
////        String url = "https://reqres.in/api/users";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        httpHeaders.add("user-agent",
//                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
//
//
//        HttpEntity<String> entity = new HttpEntity<String>("params", httpHeaders);
//        Object response = restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
//        List<NameValuePair>;
////        System.out.println(response);
//        String jsonOutput = JSON.toJSONString(response);
//        return jsonOutput;
//    }
}
