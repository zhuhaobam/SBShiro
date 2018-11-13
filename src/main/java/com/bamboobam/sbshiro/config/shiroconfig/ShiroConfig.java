package com.bamboobam.sbshiro.config.shiroconfig;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


    /**
     * 配置Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置加密匹配，使用MD5的方式，进行2次加密
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /**
     * 这是个自定义的认证类，继承子AuthorizingRealm，负责用户的认证和权限处理
     */
    @Bean(name = "bmRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public BmRealm bmRealm() {
        BmRealm bmRealm = new BmRealm();
        bmRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return bmRealm;
    }


    /**
     * 安全管理器
     * 将realm加入securityManager
     * 注：使用shiro-spring-boot-starter 1.4时，返回类型是SecurityManager会报错
     * 直接引用shiro-spring则不报错
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(bmRealm());
        return defaultWebSecurityManager;
    }


    /**
     * 配置拦截器
     * <p>
     * 定义拦截URL权限，优先级从上到下
     * 1). anon  : 匿名访问，无需登录
     * 2). authc : 登录后才能访问
     * 3). logout: 登出anon,logout
     * 4). user: 已登录或“记住我”的用户可以访问
     * 5). roles : 角色过滤器authc,roles[js]
     * 6). perms : 权限过滤器authc,perms[mvn:install]
     * <p>
     * 属于user角色
     *
     * @RequiresRoles("user") //必须同时属于user和admin角色
     * @RequiresRoles({"user", "admin"})
     * //属于user或者admin之一;修改logical为OR 即可
     * @RequiresRoles(value = {"user", "admin"}, logical = Logical.OR)
     * ---------------------
     * //符合index:hello权限要求
     * @RequiresPermissions("index:hello") //必须同时复核index:hello和index:world权限要求
     * @RequiresPermissions({"index:hello", "index:world"})
     * //符合index:hello或index:world权限要求即可
     * @RequiresPermissions(value = {"index:hello", "index:world"}, logical = Logical.OR)
     * <p>
     * URL 匹配风格
     * 1). ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/；
     * 2). *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1；
     * 3). **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        logger.info("^^^^^^^^^^^^^配置Shiro拦截工厂^^^^^^^^^^^^^");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager); // 设置 SecurityManager
        shiroFilterFactoryBean.setLoginUrl("/login");        // 登录的路径
        shiroFilterFactoryBean.setSuccessUrl("/index");    // 登录成功后跳转的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");    // 验证失败后跳转的路径
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ps/**", "anon");
        filterChainDefinitionMap.put("/role/**", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/reg", "anon");
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自动创建代理类，若不添加，Shiro的注解可能不会生效。
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启Shiro的注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}