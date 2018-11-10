package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.repositoryconfig.table.RTable;
import com.bamboobam.sbshiro.config.reposeconfig.CheckException;
import com.bamboobam.sbshiro.config.reposeconfig.ResultBean;
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
    //@RequiresRoles("role")
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<RTable> tables = (List<RTable>) roleRepository.findAll();
        List<Role> list = new ArrayList<>();
        for (RTable table : tables) {
            list.add(new Role(table));
        }
        return new ResultBean(list);
    }


    /**
     * -----------------------------------------------------
     * http://localhost:8080/role/save?id=1&name=role1&cid=1
     * http://localhost:8080/role/save?id=1&name=role1&cid=2
     * -----------------------------------------------------
     * http://localhost:8080/role/save?id=2&name=role2&cid=2
     *
     * @param id
     * @param name
     * @param cid
     * @return
     */
    //@RequiresRoles("role")
    @GetMapping(value = "save")
    public ResultBean saveOrUpdate(Long id, String name, Long[] cid) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        RTable rTable = (RTable) roleRepository.saveOrUpdate(new RTable(id, name, cid));
        Role orUpdate = new Role(rTable);
        return new ResultBean(orUpdate);
    }
}
