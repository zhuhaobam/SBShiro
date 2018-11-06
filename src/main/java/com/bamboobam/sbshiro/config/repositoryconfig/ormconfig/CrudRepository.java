package com.bamboobam.sbshiro.config.repositoryconfig.ormconfig;

import java.io.Serializable;

/**
 * 增加删除接口标准
 *
 * @param <T>
 * @param <ID>
 */
public interface CrudRepository<T, ID extends Serializable> {

    <S extends T> S saveOrUpdate(S entity);

    T findOne(ID id);

    Iterable<T> findAll();

    long count();

    boolean exists(ID id);

    void delete(ID id);

    void deleteAll();
}
