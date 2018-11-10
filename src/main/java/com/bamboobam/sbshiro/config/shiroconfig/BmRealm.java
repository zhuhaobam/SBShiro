package com.bamboobam.sbshiro.config.shiroconfig;


import com.bamboobam.sbshiro.config.reposeconfig.CheckException;
import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;
import com.bamboobam.sbshiro.entity.User;
import com.bamboobam.sbshiro.persistance.UserRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * custom	英[ˈkʌstəm] 美[ˈkʌstəm]
 * n.	习惯，惯例; 海关，关税; 经常光顾; [总称] （经常性的） 顾客;
 * adj.	（衣服等） 定做的，定制的;
 */
public class BmRealm extends AuthorizingRealm {


    @Autowired
    private UserRepository userRepository;

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        //设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setStoredCredentialsHexEncoded(false);
        hashMatcher.setHashIterations(1024);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new CheckException("PrincipalCollection method argument cannot be null.");
        }
        User user = (User) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return null;
    }


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        UTable uTable = (UTable)userRepository.findByName(usernamePasswordToken.getUsername());
        User user = new User(uTable);
        System.out.println("认证"+user.toString());
        if (uTable == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(), ByteSource.Util.bytes(user.getSalt()), getName());
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
     * 清空所有缓存
     */
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
    /**
     * 清空所有认证缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
