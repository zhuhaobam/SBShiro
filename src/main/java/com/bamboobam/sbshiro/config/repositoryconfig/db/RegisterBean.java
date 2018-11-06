package com.bamboobam.sbshiro.config.repositoryconfig.db;

import com.bamboobam.sbshiro.persistance.PermissionRepository;
import com.bamboobam.sbshiro.persistance.RoleRepository;
import com.bamboobam.sbshiro.persistance.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册bean
 *
 * @version 1.0
 */
@Configuration
public class RegisterBean {


    @Bean
    public PermissionRepository getPermissionRepository() {
        return (PermissionRepository) BeanIOCFactory.getBean("permissionRepository");
    }

    @Bean
    public RoleRepository getRoleRepository() {
        return (RoleRepository) BeanIOCFactory.getBean("roleRepository");
    }

    @Bean
    public UserRepository getUserRepository() {
        return (UserRepository) BeanIOCFactory.getBean("userRepository");
    }
}
