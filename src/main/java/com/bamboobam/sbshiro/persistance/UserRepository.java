package com.bamboobam.sbshiro.persistance;

import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.JpaRepository;
import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;
import com.bamboobam.sbshiro.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    UTable findByName(String name);

    List<UTable> findByNameLike(String name);

}
