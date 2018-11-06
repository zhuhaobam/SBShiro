package com.bamboobam.sbshiro.controller;

import com.bamboobam.sbshiro.config.resultconfig.CheckException;
import com.bamboobam.sbshiro.config.resultconfig.ResultBean;
import com.bamboobam.sbshiro.entity.Role;
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

    //http://localhost:8080/user/findall
    @GetMapping(value = "findall")
    public ResultBean findAll() {
        List<User> userList = (List<User>) userRepository.findAll();
        return new ResultBean(userList);
    }


    /**
     * http://localhost:8080/user/save?id=1&name=admin&pwd=123456&cid=1
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
    public ResultBean saveOrUpdate(Long id, String name, String pwd, Long cid) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型参数最小为1");
        }
        Role role = (Role) roleRepository.findOne(cid);
        if (null == role) {
            throw new CheckException("role不存在cid:" + cid);
        }
        List<Role> manys = new ArrayList<>();
        User _User = (User) userRepository.findOne(id);
        if (null == _User) {
            manys.add(role);
        } else if (!_User.getRoles().contains(role)) {
            manys.addAll(_User.getRoles());
            manys.add(role);
        }
        User orUpdate = (User) userRepository.saveOrUpdate(new User(id, name, pwd, manys));
        return new ResultBean(orUpdate);
    }
}
