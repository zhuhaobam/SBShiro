package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.reposeconfig.CheckException;
import com.bamboobam.sbshiro.config.reposeconfig.ResultBean;
import com.bamboobam.sbshiro.config.repositoryconfig.table.PTable;
import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/ps")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    //http://localhost:8080/ps/findone?id=1
    //@RequiresPermissions("ps:find")
    @GetMapping(value = "findone")
    public ResultBean findOne(Long id) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        return new ResultBean(new Permission((PTable) permissionRepository.findOne(id)));
    }

    //http://localhost:8080/ps/findall
    //@RequiresPermissions("ps:find")
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<PTable> tables = (List<PTable>) permissionRepository.findAll();
        List<Permission> list = new ArrayList<>();
        for (PTable table : tables) {
            list.add(new Permission(table));
        }
        return new ResultBean(list);
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
    //@RequiresPermissions("ps:save")
    @GetMapping(value = "save")
    public ResultBean saveOrUpdate(Long id, String name, String url) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        PTable pTable = (PTable) permissionRepository.saveOrUpdate(new PTable(id, name, "/" + url));
        Permission orUpdate = new Permission(pTable);
        return new ResultBean(orUpdate);
    }
}
