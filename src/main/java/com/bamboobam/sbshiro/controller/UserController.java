package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.Constant;
import com.bamboobam.sbshiro.config.reposeconfig.CheckException;
import com.bamboobam.sbshiro.config.reposeconfig.ResultBean;
import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;
import com.bamboobam.sbshiro.entity.User;
import com.bamboobam.sbshiro.persistance.PermissionRepository;
import com.bamboobam.sbshiro.persistance.RoleRepository;
import com.bamboobam.sbshiro.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @GetMapping(value = "findname")
    public ResultBean findByName(String name) {
        UTable uTable = (UTable) userRepository.findByName(name);
        return new ResultBean(new User(uTable));
    }

    @GetMapping(value = "findlikename")
    public ResultBean findByNameLike(String name) {
        List<UTable> tables = (List<UTable>) userRepository.findByNameLike(name);
        List<User> list = new ArrayList<>();
        for (UTable utable : tables) {
            list.add(new User(utable));
        }
        return new ResultBean(list);
    }

    //http://localhost:8080/user/findall
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<UTable> tables = (List<UTable>) userRepository.findAll();
        List<User> list = new ArrayList<>();
        for (UTable utable : tables) {
            list.add(new User(utable));
        }
        return new ResultBean(list);
    }


    /**
     * http://localhost:8080/user/save?id=5&name=admin&pwd=123456&salt=tt&locked=true&cid=1,2,3
     * ----------------------------------------------------------------
     * http://localhost:8080/user/save?id=2&name=lala&pwd=123456&cid=2
     *
     * @param id
     * @param name
     * @param pwd
     * @param cid
     * @return
     */
    @GetMapping(value = "save")
    public ResultBean saveOrUpdate(Long id, String name, String pwd, String salt, Boolean locked, Long[] cid) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型参数最小为1");
        }
        UTable uTable = (UTable) userRepository.saveOrUpdate(new UTable(id, name, Constant.getPwd(name, pwd, salt), salt, locked, cid));
        User orUpdate = new User(uTable);
        return new ResultBean(orUpdate);
    }


}
