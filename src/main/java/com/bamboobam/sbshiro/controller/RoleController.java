package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.resultconfig.CheckException;
import com.bamboobam.sbshiro.config.resultconfig.ResultBean;
import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.entity.Role;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import com.bamboobam.sbshiro.persistance.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    //http://localhost:8080/role/findall
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<Role> roleList = (List<Role>) roleRepository.findAll();
        return new ResultBean(roleList);
    }


    /**
     * -----------------------------------------------------
     * http://localhost:8080/role/save?id=1&name=role1&cid=1
     * http://localhost:8080/role/save?id=1&name=role1&cid=2
     * -----------------------------------------------------
     * http://localhost:8080/role/save?id=2&name=role2&cid=2
     * @param id
     * @param name
     * @param cid
     * @return
     */
    @GetMapping(value = "save")
    public ResultBean saveOrUpdate(Long id, String name, Long cid) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        Permission permission = (Permission) permissionRepository.findOne(cid);
        if (null == permission) {
            throw new CheckException("permission不存在cid:" + cid);
        }
        List<Permission> manys = new ArrayList<>();
        Role _Role = (Role) roleRepository.findOne(id);
        if (null == _Role) {
            manys.add(permission);
        } else if (!_Role.getPermissions().contains(permission)) {
            manys.addAll(_Role.getPermissions());
            manys.add(permission);
        }
        Role orUpdate = (Role) roleRepository.saveOrUpdate(new Role(id, name, manys));
        return new ResultBean(orUpdate);
    }
}
