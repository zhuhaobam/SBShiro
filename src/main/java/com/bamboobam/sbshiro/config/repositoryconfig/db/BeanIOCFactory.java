package com.bamboobam.sbshiro.config.repositoryconfig.db;

import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.BeanDefinition;
import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.ProxyRepository;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import com.bamboobam.sbshiro.persistance.RoleRepository;
import com.bamboobam.sbshiro.persistance.UserRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据仓库bean工厂
 * 1.自定义实现标准
 * 2.可xml或者javaconfig动态配置
 *
 * @version 0.1 【后续使用配置文件】
 */
public class BeanIOCFactory {

    //映射关系【解耦-自动映射（实现类方法名和接口方法要一致）】
    //此处都是StandardRepository标准实现，你可以自定义不同的实现
    private static Map<String, BeanDefinition> mapRepository = new HashMap<>();
    private static Map<String, Object> mapBean = new HashMap<>();//缓存代理对象


    static {
        mapRepository.put("permissionRepository", new BeanDefinition(PermissionRepository.class, StandardRepository.class));
        mapRepository.put("roleRepository", new BeanDefinition(RoleRepository.class, StandardRepository.class));
        mapRepository.put("userRepository", new BeanDefinition(UserRepository.class, StandardRepository.class));
    }

    public static Object getBean(String beanname) {
        Object robj = null;
        if (!mapBean.containsKey(beanname)) {
            if (!mapRepository.containsKey(beanname)) {
                throw new RuntimeException("找不到Bean");
            }
            BeanDefinition beanDefinition = mapRepository.get(beanname);
            Object object = ProxyRepository.newMapperProxy(beanDefinition.getClazzRepository(),
                    beanDefinition.getClazzDataSoure());
            mapBean.put(beanname, object);
            robj = object;
        } else {
            robj = mapBean.get(beanname);
        }
        mapBean.size();
        return robj;
    }
}
