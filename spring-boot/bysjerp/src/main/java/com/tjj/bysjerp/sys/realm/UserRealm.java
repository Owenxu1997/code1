package com.tjj.bysjerp.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjj.bysjerp.sys.common.ActiverUser;
import com.tjj.bysjerp.sys.common.Constant;
import com.tjj.bysjerp.sys.domain.Permission;
import com.tjj.bysjerp.sys.domain.User;
import com.tjj.bysjerp.sys.service.PermissionService;
import com.tjj.bysjerp.sys.service.RoleService;
import com.tjj.bysjerp.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Owen on 2020/4/6 21:47
 *自定义UserRealm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy  //只有使用的时候才会加载
    private UserService userService;

    @Autowired
    @Lazy
    private PermissionService permissionService;//只需要拿权限编码

    @Autowired
    @Lazy
    private RoleService roleService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 认证用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",token.getPrincipal().toString());
        User user = userService.getOne(queryWrapper);
        if (null!=user) {
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);

            //根据用户ID查询permissioncode
            //查询所有权限
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //设置只能查权限
            qw.eq("type", Constant.TYPE_PERMISSION);
            qw.eq("available",Constant.AVAILABLE_TRUE);
            //根据用户id+角色+权限去查询(user关联role,role关联permission)
            Integer userId = user.getId();
            //根据用户Id查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //根据角色id查询权限和菜单id
            Set<Integer> pids = new HashSet<>();//pid去重
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            //根据角色id查询权限
            if (pids.size()>0) {
                qw.in("id",pids);
                list=permissionService.list(qw);
            }
            List<String> permissioncodes = new ArrayList<>();
            for (Permission permission : list) {
                permissioncodes.add(permission.getPermissioncode());
            }
            //放到
            activerUser.setPermissions(permissioncodes);

            //此处salt代表的是创建(添加)用户生成的加密hash码
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPassword(),credentialsSalt,this.getName());
            return info;
        }

        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
        User user  = activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if (user.getType()==Constant.USER_TYPE_SUPER) {
            authorizationInfo.addStringPermission("*:*");
        }else {
            if (null != permissions&&permissions.size()>0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }


}
