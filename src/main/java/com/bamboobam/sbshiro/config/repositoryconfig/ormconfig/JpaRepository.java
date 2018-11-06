package com.bamboobam.sbshiro.config.repositoryconfig.ormconfig;

import java.io.Serializable;


/**
 * 外部使用接口，屏蔽内部接口
 *
 * @param <T>
 * @param <ID>
 */
public interface JpaRepository<T, ID extends Serializable> extends CrudRepository {


}
