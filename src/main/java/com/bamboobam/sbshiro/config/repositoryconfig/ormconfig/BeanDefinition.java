package com.bamboobam.sbshiro.config.repositoryconfig.ormconfig;

/**
 * bean
 */
public class BeanDefinition {


    private Class<?> clazzRepository;
    private Class<?> clazzDataSoure;

    public Class<?> getClazzRepository() {
        return clazzRepository;
    }

    public Class<?> getClazzDataSoure() {
        return clazzDataSoure;
    }


    public BeanDefinition(Class<?> clazzRepository, Class<?> clazzDataSoure) {
        this.clazzRepository = clazzRepository;
        this.clazzDataSoure = clazzDataSoure;
    }
}
