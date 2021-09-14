package com.tjj.bysjerp.sys.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Owen on 2020/4/7 1:54
 *
 * 工具类
 */
public class WebUtils {
    /**
     * 得到request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }
    /**
     * 得到session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }


}
