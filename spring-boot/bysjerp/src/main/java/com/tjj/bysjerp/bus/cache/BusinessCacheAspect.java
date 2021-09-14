package com.tjj.bysjerp.bus.cache;

import com.tjj.bysjerp.bus.domain.Customer;
import com.tjj.bysjerp.bus.domain.Goods;
import com.tjj.bysjerp.bus.domain.Provider;
import com.tjj.bysjerp.sys.cache.CachePool;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owen on 2020/4/16 18:06
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class BusinessCacheAspect {
    /**
     * 日志出处
     * @author tjj
     * @date 2020/4/12 19:28
     */
    private Log log = LogFactory.getLog(BusinessCacheAspect.class);

    //声明一个缓存容器
    private static Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;


    //客户切面表达式
    private static final String POINTCUT_CUSTOMER_ADD = "execution(* com.tjj.bysjerp.bus.service.impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_UPDATE = "execution(* com.tjj.bysjerp.bus.service.impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_GET = "execution(* com.tjj.bysjerp.bus.service.impl.CustomerServiceImpl.getById(..))";
    private static final String POINTCUT_CUSTOMER_DELETE = "execution(* com.tjj.bysjerp.bus.service.impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_BATCHDELETE = "execution(* com.tjj.bysjerp.bus.service.impl.CustomerServiceImpl.removeByIds(..))";

    private static final String CACHE_CUSTOMER_PREFIX = "customer:";

    /**
     * 客户查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PREFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到客户对象"+CACHE_CUSTOMER_PREFIX + object);
            return res1;
        } else {
            Customer res2 = (Customer) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + res2.getId(), res2);
            log.info("未从缓存中找到客户对象，去到数据库查询并加入缓存"+CACHE_CUSTOMER_PREFIX+res2.getId());
            return res2;
        }
    }


    /**
     * 客户添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_ADD)
    public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Customer object = (Customer) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //mybatisPlus 里目标方法执行完后id自动赋值
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + object.getId(), object);
        }
        return res;
    }


    /**
     * 客户更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Customer customerVo = (Customer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        //从缓存里面取
        if (isSuccess) {
            Customer customer = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PREFIX + customerVo.getId());
            if (null == customer) {
                customer = new Customer();
            }
            BeanUtils.copyProperties(customerVo, customer);
            log.info("客户对象缓存已更新"+CACHE_CUSTOMER_PREFIX + customerVo.getId());
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + customer.getId(), customer);
        }
        return isSuccess;
    }

    /**
     * 客户删除切入
     * @author tjj
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_CUSTOMER_DELETE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_PREFIX + id);
            log.info("客户对象缓存已删除"+CACHE_CUSTOMER_PREFIX + id);

        }
        return isSuccess;
    }
    /**
     * 客户删除切入
     * @author tjj
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_CUSTOMER_BATCHDELETE)
    public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Collection<Serializable> idList= (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            for (Serializable id : idList) {
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PREFIX + id);
                log.info("客户对象缓存已删除"+CACHE_CUSTOMER_PREFIX + id);
            }
        }
        return isSuccess;
    }

    //供应商切面表达式
    private static final String POINTCUT_PROVIDER_ADD = "execution(* com.tjj.bysjerp.bus.service.impl.ProviderServiceImpl.save(..))";
    private static final String POINTCUT_PROVIDER_UPDATE = "execution(* com.tjj.bysjerp.bus.service.impl.ProviderServiceImpl.updateById(..))";
    private static final String POINTCUT_PROVIDER_GET = "execution(* com.tjj.bysjerp.bus.service.impl.ProviderServiceImpl.getById(..))";
    private static final String POINTCUT_PROVIDER_DELETE = "execution(* com.tjj.bysjerp.bus.service.impl.ProviderServiceImpl.removeById(..))";
    private static final String POINTCUT_PROVIDER_BATCHDELETE = "execution(* com.tjj.bysjerp.bus.service.impl.ProviderServiceImpl.removeByIds(..))";

    private static final String CACHE_PROVIDER_PREFIX = "provider:";

    /**
     * 供应商查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_GET)
    public Object cacheProviderGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_PROVIDER_PREFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到供应商对象"+CACHE_PROVIDER_PREFIX + object);
            return res1;
        } else {
            Provider res2 = (Provider) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_PROVIDER_PREFIX + res2.getId(), res2);
            log.info("未从缓存中找到供应商对象，去到数据库查询并加入缓存"+CACHE_PROVIDER_PREFIX+res2.getId());
            return res2;
        }
    }


    /**
     * 供应商添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_ADD)
    public Object cacheProviderAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Provider object = (Provider) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //mybatisPlus 里目标方法执行完后id自动赋值
            CACHE_CONTAINER.put(CACHE_PROVIDER_PREFIX + object.getId(), object);
        }
        return res;
    }


    /**
     * 供应商更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_PROVIDER_UPDATE)
    public Object cacheProviderUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Provider providerVo = (Provider) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        //从缓存里面取
        if (isSuccess) {
            Provider provider = (Provider) CACHE_CONTAINER.get(CACHE_PROVIDER_PREFIX + providerVo.getId());
            if (null == provider) {
                provider = new Provider();
            }
            BeanUtils.copyProperties(providerVo, provider);
            log.info("供应商对象缓存已更新"+CACHE_PROVIDER_PREFIX + providerVo.getId());
            CACHE_CONTAINER.put(CACHE_PROVIDER_PREFIX + provider.getId(), provider);
        }
        return isSuccess;
    }

    /**
     * 供应商删除切入
     * @author tjj
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_PROVIDER_DELETE)
    public Object cacheProviderDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_PROVIDER_PREFIX + id);
            log.info("供应商对象缓存已删除"+CACHE_PROVIDER_PREFIX + id);

        }
        return isSuccess;
    }
    /**
     * 供应商批量删除切入
     * @author tjj
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_PROVIDER_BATCHDELETE)
    public Object cacheProviderBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            for (Serializable id : idList) {
                CACHE_CONTAINER.remove(CACHE_PROVIDER_PREFIX + id);
                log.info("供应商对象缓存已删除" + CACHE_PROVIDER_PREFIX + id);
            }
        }
        return isSuccess;

    }


    //商品数据缓存切面表达式
    private static final String POINTCUT_GOODS_ADD = "execution(* com.tjj.bysjerp.bus.service.impl.GoodsServiceImpl.save(..))";
    private static final String POINTCUT_GOODS_UPDATE = "execution(* com.tjj.bysjerp.bus.service.impl.GoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_GET = "execution(* com.tjj.bysjerp.bus.service.impl.GoodsServiceImpl.getById(..))";
    private static final String POINTCUT_GOODS_DELETE = "execution(* com.tjj.bysjerp.bus.service.impl.GoodsServiceImpl.removeById(..))";

    private static final String CACHE_GOODS_PREFIX = "goods:";

    /**
     * 商品查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_GET)
    public Object cacheGoodsGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_PREFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到商品对象"+CACHE_GOODS_PREFIX + object);
            return res1;
        } else {
            Goods res2 = (Goods) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_GOODS_PREFIX + res2.getId(), res2);
            log.info("未从缓存中找到商品对象，去到数据库查询并加入缓存"+CACHE_GOODS_PREFIX+res2.getId());
            return res2;
        }
    }


    /**
     * 商品添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_ADD)
    public Object cacheGoodsAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Goods object = (Goods) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //mybatisPlus 里目标方法执行完后id自动赋值
            CACHE_CONTAINER.put(CACHE_GOODS_PREFIX + object.getId(), object);
        }
        return res;
    }


    /**
     * 商品更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_UPDATE)
    public Object cacheGoodsUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Goods goodsVo = (Goods) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        //从缓存里面取
        if (isSuccess) {
            Goods goods = (Goods) CACHE_CONTAINER.get(CACHE_GOODS_PREFIX + goodsVo.getId());
            if (null == goods) {
                goods = new Goods();
            }
            BeanUtils.copyProperties(goodsVo, goods);
            log.info("商品对象缓存已更新"+CACHE_GOODS_PREFIX + goodsVo.getId());
            CACHE_CONTAINER.put(CACHE_GOODS_PREFIX + goods.getId(), goods);
        }
        return isSuccess;
    }

     /**
     *商品删除切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_GOODS_DELETE)
    public Object cacheGoodsDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_GOODS_PREFIX + id);
            log.info("商品对象缓存已删除"+CACHE_GOODS_PREFIX + id);

        }
        return isSuccess;
    }

}
