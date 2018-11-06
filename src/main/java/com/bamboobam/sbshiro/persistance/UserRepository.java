package com.bamboobam.sbshiro.persistance;

import com.bamboobam.sbshiro.entity.User;
import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
