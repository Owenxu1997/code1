package com.tjj.bysjerp.sys.common;

import sun.net.www.content.image.png;

/**
 * Created by Owen on 2020/4/7 1:40
 */
public interface Constant {
    /**
     * 状态码常量
     */
    public static final Integer OK = 200;
    public static final Integer ERROR = -1;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PASSWORD = "123456";
    /**
     * 菜单权限类型
     */
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /**
     * 是否可行
     */
    public static final Object AVAILABLE_TRUE = 1;
    public static final Object AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     */
    public static final Integer USER_TYPE_SUPER = 0;
    public static final Integer USER_TYPE_NORMAL = 1;

    /**
     * 展开类型
     */
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE = 0;

    /**
     * 商品默认图
     */
    public static final String IMAGES_DEFAULT_PNG = "images/default.png";

    /**
     * hash迭代次数
     */
    public static final Integer HASH_ITERATIONS = 2;

    /**
     * 用户默认图
     */
    public static final String DEFAULT_IMAGES_USER = "images/face.jpg";
}
