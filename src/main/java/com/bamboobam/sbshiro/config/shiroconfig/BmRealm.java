package com.bamboobam.sbshiro.config.shiroconfig;


import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;
import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.entity.Role;
import com.bamboobam.sbshiro.entity.User;
import com.bamboobam.sbshiro.persistance.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * custom	英[ˈkʌstəm] 美[ˈkʌstəm]
 * n.	习惯，惯例; 海关，关税; 经常光顾; [总称] （经常性的） 顾客;
 * adj.	（衣服等） 定做的，定制的;
 */
public class BmRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizingRealm.class);

    @Autowired
    private UserRepository userRepository;


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        UTable uTable = (UTable) userRepository.findByName(username);
        if (uTable == null) {
            return null;
        }
        User user = (User) getAvailablePrincipal(principals);
        logger.info("授权" + user.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //角色
        Set<String> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            logger.info("授权角色：" + role.getName());
        }
        //权限
        Set<String> permissions = new HashSet<>();
        Set<Role> mainRoles = user.getRoles();
        for (Role role : mainRoles) {
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getPermissionname());
                logger.info("授权权限：" + permission.getPermissionname());
            }
        }
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //String username = (String) token.getPrincipal(); // 获取用户登录账号
        String username = usernamePasswordToken.getUsername();
        if (username == null) {
            return null;
        }
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UTable uTable = (UTable) userRepository.findByName(usernamePasswordToken.getUsername());
        if (uTable == null) {
            throw new UnknownAccountException("账号不存在");
        }
        User user = new User(uTable);
        if (Boolean.TRUE == user.getLocked()) {
            throw new LockedAccountException("帐号锁定");
        }
        logger.info("认证" + user.toString());
        // 1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        Object principal = username;
        // 2). credentials: 加密后的密码.
        Object credentials = user.getPassword();
        // 3). realmName: 当前 realm 对象的唯一名字. 调用父类的 getName() 方法
        String realmName = getName();
        // 4). credentialsSalt: 盐值. 注意类型是ByteSource
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername() + user.getSalt());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 更新用户信息缓存.
     */
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }


    /**
     * 清空所有缓存
     * 当管理员修改了用户的权限，但是该用户还没有退出，在默认情况下，修改的权限无法立即生效。
     * 需要手动进行编程实现：在权限修改后调用realm的clearCache方法清除缓存。
     * PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
     * super.clearCache(principals);
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 清除用户授权信息缓存.
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 清除用户信息缓存.
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }


    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
