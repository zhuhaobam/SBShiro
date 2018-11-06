package com.bamboobam.sbshiro.persistance;

import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.JpaRepository;
import com.bamboobam.sbshiro.entity.Permission;
import org.springframework.stereotype.Component;

@Component
public interface PermissionRepository extends JpaRepository<Permission, Long> {


}
