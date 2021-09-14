package com.tjj.bysjerp;

import com.sun.org.apache.regexp.internal.RE;
import com.tjj.bysjerp.bus.service.ApiTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BysjerpApplicationTests {

    @Autowired
    ApiTestService apiTestService;

    @Test
    void contextLoads() {
    }

    @Test
    void get() {
        String url = "https://reqres.in/api/users";
//        apiTestService.get(url);
    }


    @Test
    void getTest(){
        String url = "https://reqres.in/api/users";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("params", httpHeaders);
        Object response = restTemplate.exchange(url,HttpMethod.GET,entity,Object.class);
        System.out.println(httpHeaders);
        System.out.println(response);
    }

    @Test
    void getWithParam() {
        String url = "https://reqres.in/api/users/{id}";
        RestTemplate restTemplate = new RestTemplate();

        Map<String,Object> uriVariables = new HashMap<>();
        uriVariables.put("id",2);

        Object result = restTemplate.getForObject(url,Object.class,uriVariables);
        System.out.println(result);


    }



}
