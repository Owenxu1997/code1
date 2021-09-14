package com.tjj.bysjerp.sys.cache;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjj.bysjerp.bus.domain.Customer;
import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Provider;
import com.tjj.bysjerp.bus.mapper.CustomerMapper;
import com.tjj.bysjerp.bus.mapper.GoodsMapper;
import com.tjj.bysjerp.bus.mapper.ProviderMapper;
import com.tjj.bysjerp.sys.common.SpringUtil;
import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.domain.User;
import com.tjj.bysjerp.sys.mapper.DepartmentMapper;
import com.tjj.bysjerp.sys.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存
 * Created by Owen on 2020/4/19 20:17
 */
public class CachePool {

    /**
     * 所有的缓存对象放到这个CACHE_CONTAINER类似于redis
     */
    public static volatile Map<String, Object> CACHE_CONTAINER = new HashMap<>();

    /**
     * 根据Key删除缓存
     * @param key
     */
    public static void removeCacheByKey(String key) {
        if (CACHE_CONTAINER.containsKey(key)) {
            CACHE_CONTAINER.remove(key);
        }
    }

    /**
     * 清空缓存
     */
    public static void removeAllCache() {
        CACHE_CONTAINER.clear();

    }

    /**
     * 同步缓存
     * （原理跟反射差不多）
     * 先获取数据，然后查询
     * 实体类也得补充toString方法
     */
    public static void syncData() {
        //同步部门数据
        DepartmentMapper departmentMapper = SpringUtil.getBean(DepartmentMapper.class);
        List<Department> departmentList = departmentMapper.selectList(null);
        for (Department department : departmentList) {
            CACHE_CONTAINER.put("department:"+department.getId(),department);
        }
        //同步用户数据
        UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            CACHE_CONTAINER.put("user:"+user.getId(),user);
        }
        //同步客户数据
        CustomerMapper customerMapper = SpringUtil.getBean(CustomerMapper.class);
        List<Customer> customerList = customerMapper.selectList(null);
        for (Customer customer : customerList) {
            CACHE_CONTAINER.put("customer:"+customer.getId(),customer);
        }
        //同步供应商数据
        ProviderMapper providerMapper = SpringUtil.getBean(ProviderMapper.class);
        List<Provider> providerList = providerMapper.selectList(null);
        for (Provider provider : providerList) {
            CACHE_CONTAINER.put("provider:"+provider.getId(),provider);
        }
        //同步商品数据
        GoodsMapper goodsMapper = SpringUtil.getBean(GoodsMapper.class);
        List<Goods> goodsList = goodsMapper.selectList(null);
        for (Goods goods : goodsList) {
            CACHE_CONTAINER.put("goods:"+goods.getId(),goods);
        }

    }

}
