package com.tjj.bysjerp.sys.controller;

import com.tjj.bysjerp.sys.cache.CachePool;
import com.tjj.bysjerp.sys.common.CacheBean;
import com.tjj.bysjerp.sys.common.DataGridView;
import com.tjj.bysjerp.sys.common.ResultObj;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Owen on 2020/4/19 22:47
 * 缓存管理控制器
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    public static volatile Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    /**
     * 查询所有缓存
     */
    @RequestMapping("loadAllCache")
    public DataGridView loadAllCache() {
        List<CacheBean> list = new ArrayList<>();

        Set<Map.Entry<String, Object>> entrySet = CACHE_CONTAINER.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            list.add(new CacheBean(entry.getKey(),entry.getValue()));
        }
        return new DataGridView(list);
        }

    /**
     * 删除缓存
     */
    @RequestMapping("deleteCache")
    public ResultObj deleteCache(String key) {
      CachePool.removeCacheByKey(key);
      return ResultObj.DELETE_SUCCESS;
    }

    /**
     * 清空缓存
     */
    @RequestMapping("removeAllCache")
    public ResultObj removeAllCache() {
        CachePool.removeAllCache();
        return ResultObj.DELETE_SUCCESS;
    }

    /**
     * 同步缓存
     */
    @RequestMapping("syncCache")
    public ResultObj syncCache() {
        CachePool.syncData();
        return ResultObj.OPERATE_SUCCESS;
    }

    }

