package com.tjj.bysjerp.sys.cache;

import com.tjj.bysjerp.sys.domain.Department;
import com.tjj.bysjerp.sys.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owen on 2020/4/10 13:49
 */


@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    /**
     * 日志出处
     * @author owen
     * @date 2020/4/12 19:28
     */
    private Log log = LogFactory.getLog(CacheAspect.class);

    //声明一个缓存容器
    private static Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;


    //声明一个切面表达式
    private static final String POINTCUT_DEPARTMENT_ADD = "execution(* com.tjj.bysjerp.sys.service.impl.DepartmentServiceImpl.save(..))";
    private static final String POINTCUT_DEPARTMENT_UPDATE = "execution(* com.tjj.bysjerp.sys.service.impl.DepartmentServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPARTMENT_GET = "execution(* com.tjj.bysjerp.sys.service.impl.DepartmentServiceImpl.getById(..))";
    private static final String POINTCUT_DEPARTMENT_DELETE = "execution(* com.tjj.bysjerp.sys.service.impl.DepartmentServiceImpl.removeById(..))";

    private static final String CACHE_DEPARTMENT_PREFIX = "department:";

    /**
     * 部门查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPARTMENT_GET)
    public Object cacheDepartmentGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPARTMENT_PREFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到部门对象"+CACHE_DEPARTMENT_PREFIX + object);
            return res1;
        } else {
            Department res2 = (Department) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPARTMENT_PREFIX + res2.getId(), res2);
            log.info("未从缓存中找到部门对象，去到数据库查询并加入缓存"+CACHE_DEPARTMENT_PREFIX+res2.getId());
            return res2;
        }
    }


    /**
     * 部门添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPARTMENT_ADD)
    public Object cacheDepartmentAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Department object = (Department) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //mybatisPlus 里目标方法执行完后id自动赋值
            CACHE_CONTAINER.put(CACHE_DEPARTMENT_PREFIX + object.getId(), object);
        }
        return res;
    }


    /**
     * 部门更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPARTMENT_UPDATE)
    public Object cacheDepartmentUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Department departmentVo = (Department) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        //从缓存里面取
        if (isSuccess) {
            Department department = (Department) CACHE_CONTAINER.get(CACHE_DEPARTMENT_PREFIX + departmentVo.getId());
            if (null == department) {
                department = new Department();
            }
            BeanUtils.copyProperties(departmentVo, department);
            log.info("部门对象缓存已更新"+CACHE_DEPARTMENT_PREFIX + departmentVo.getId());
            CACHE_CONTAINER.put(CACHE_DEPARTMENT_PREFIX + department.getId(), department);
        }
        return isSuccess;
    }

    /**
     * 部门删除切入
     * @author owen
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_DEPARTMENT_DELETE)
    public Object cacheDepartmentDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPARTMENT_PREFIX + id);
            log.info("部门对象缓存已删除"+CACHE_DEPARTMENT_PREFIX + id);

        }
        return isSuccess;
    }




    //用户切面
    private static final String POINTCUT_USER_ADD = "execution(* com.tjj.bysjerp.sys.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_UPDATE = "execution(* com.tjj.bysjerp.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.tjj.bysjerp.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.tjj.bysjerp.sys.service.impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_PREFIX = "user:";
    
    /**
     * 用户添加切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User object = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            //mybatisPlus 里目标方法执行完后id自动赋值
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 用户查询切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_PREFIX + object);
        if (res1 != null) {
            log.info("已从缓存中找到用户对象"+CACHE_USER_PREFIX + object);
            return res1;
        } else {
            User res2 = (User) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + res2.getId(), res2);
            log.info("未从缓存中找到用户对象，去到数据库查询并加入缓存"+CACHE_USER_PREFIX+res2.getId());
            return res2;
        }
    }

    /**
     * 用户更新切入
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User userVo = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        //从缓存里面取
        if (isSuccess) {
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_PREFIX + userVo.getId());
            if (null == user) {
                user = new User();
            }
            BeanUtils.copyProperties(userVo, user);
            log.info("用户对象缓存已更新"+CACHE_USER_PREFIX + userVo.getId());
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + user.getId(), user);
        }
        return isSuccess;
    }
    
    /**
     * 用户删除切入
     * @author owen
     * @date 2020/4/12 19:38
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_USER_PREFIX + id);
            log.info("用户对象缓存已删除"+CACHE_USER_PREFIX + id);


        }
        return isSuccess;
    }
}
