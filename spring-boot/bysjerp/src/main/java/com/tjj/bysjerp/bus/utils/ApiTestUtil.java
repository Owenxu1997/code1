package com.tjj.bysjerp.bus.utils;

import com.tjj.bysjerp.bus.vo.ResultVO;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ApiTestUtil {

    public ResultVO PostRequest(String url, MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        //表单方式提交post请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将头部和参数合并为一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //
        ResponseEntity<ResultVO> response = client.exchange(url, method, requestEntity, ResultVO.class);

        return response.getBody();
    }
}
