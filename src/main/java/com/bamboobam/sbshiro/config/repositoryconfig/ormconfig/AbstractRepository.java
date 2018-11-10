package com.bamboobam.sbshiro.config.repositoryconfig.ormconfig;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 约束Orm实现
 */
public abstract class AbstractRepository {

    public abstract Object repositoryProxyTransfer(String method, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    public abstract Object saveOrUpdate(Object entity);

    public abstract List<Object> findAll();

    public abstract Object findOne(Long id);

    public abstract Object findByName(String name);

    public abstract List<Object> findByNameLike(String name);

   /*


    public abstract long count();

    public abstract boolean exists(Long id);

    public abstract void delete(Long id);

    public abstract void deleteAll();*/
}
