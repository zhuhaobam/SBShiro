package com.bamboobam.sbshiro;

import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import com.bamboobam.sbshiro.persistance.RoleRepository;
import com.bamboobam.sbshiro.persistance.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbshiroApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    @Test
    public void repositorySaveOrUpdate() {
        Permission psobj = (Permission) permissionRepository.saveOrUpdate(new Permission(4L, "good", "/good"));
    }


    @Test
    public void repositoryFindOne() {
        Permission psobj1 = (Permission) permissionRepository.findOne(1L);
    }

    @Test
    public void repositoryFindAll() {
        List<Permission> psobjs = (List<Permission>) permissionRepository.findAll();
    }

    @Test
    public void repositoryExists() {
        permissionRepository.exists(1L);
    }

    @Test
    public void repositoryDelete() {
        permissionRepository.delete(1L);
        permissionRepository.findAll();
    }


    @Test
    public void repositoryRoleFindOne() {
        roleRepository.findOne(1L);

    }

}
