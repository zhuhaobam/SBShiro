package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.resultconfig.CheckException;
import com.bamboobam.sbshiro.config.resultconfig.ResultBean;
import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ps")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    //http://localhost:8080/ps/findone?id=1
    @GetMapping(value = "findone")
    public ResultBean findOne(Long id) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        return new ResultBean((Permission) permissionRepository.findOne(id));
    }

    //http://localhost:8080/ps/findall
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<Permission> permissionList = (List<Permission>) permissionRepository.findAll();
        return new ResultBean(permissionList);
    }


    /**
     * -----------------------------------------------------
     * http://localhost:8080/ps/save?id=1&name=pname1&url=purl1
     * -----------------------------------------------------
     * http://localhost:8080/ps/save?id=2&name=pname2&url=purl2
     * -----------------------------------------------------
     * http://localhost:8080/ps/save?id=3&name=pname3&url=purl3
     *
     * @param id
     * @param name
     * @param url
     * @return
     */
    @GetMapping(value = "save")
    public ResultBean saveOrUpdate(Long id, String name, String url) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        Permission orUpdate = (Permission) permissionRepository.saveOrUpdate(new Permission(id, name, "/" + url));
        return new ResultBean(orUpdate);
    }
}
