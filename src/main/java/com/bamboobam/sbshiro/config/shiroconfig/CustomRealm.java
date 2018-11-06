package com.bamboobam.sbshiro.config.shiroconfig;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * custom	英[ˈkʌstəm] 美[ˈkʌstəm]
 * n.	习惯，惯例; 海关，关税; 经常光顾; [总称] （经常性的） 顾客;
 * adj.	（衣服等） 定做的，定制的;
 */
public class CustomRealm  extends AuthorizingRealm {


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }
}
