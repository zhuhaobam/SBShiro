package com.bamboobam.sbshiro.entity;

import com.bamboobam.sbshiro.config.Constant;
import com.bamboobam.sbshiro.config.repositoryconfig.db.Bmdb;
import com.bamboobam.sbshiro.config.repositoryconfig.table.RTable;
import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;

import java.util.HashSet;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String password;
    private String salt;// 加密盐值
    private Boolean locked = Boolean.FALSE;
    private Set<Role> roles;
    public static Bmdb BMDB = Bmdb.getInstance();

    public User() {
    }

    public User(UTable uTable) {
        this.id = uTable.getId();
        this.username = uTable.getUsername();
        this.password = uTable.getPassword();
        this.salt = uTable.getSalt();
        this.locked = uTable.getLocked();
        Set<Role> roles = new HashSet<>();
        for (long aid : uTable.getRoles()) {
            roles.add(new Role((RTable) BMDB.SelectById(Constant.T_ROLE, aid)));
        }
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        User user = new User((UTable) BMDB.SelectById(Constant.T_USER, id));
        return "{\"User\":{" +
                "\"id\":\"" + id +
                "\", username\":\"" + username +
                "\", password\":\"" + password +
                "\", salt\":\"" + salt +
                "\", locked\":\"" + locked +
                "\", roles\":" + user.getRoles() +
                "}}";
    }
}
