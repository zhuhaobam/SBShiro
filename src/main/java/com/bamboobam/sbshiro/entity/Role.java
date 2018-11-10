package com.bamboobam.sbshiro.entity;

import com.bamboobam.sbshiro.config.Constant;
import com.bamboobam.sbshiro.config.repositoryconfig.db.Bmdb;
import com.bamboobam.sbshiro.config.repositoryconfig.table.PTable;
import com.bamboobam.sbshiro.config.repositoryconfig.table.RTable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Role {

    private Long id;
    private String name;
    private Set<Permission> permissions;

    public static Bmdb BMDB = Bmdb.getInstance();

    public Role() {
    }

    public Role(RTable tRole) {
        this.id = tRole.getId();
        this.name = tRole.getName();
        Set<Permission> permissions = new HashSet<>();
        for (long aid : tRole.getPermissions()) {
            permissions.add(new Permission((PTable) BMDB.SelectById(Constant.T_PERMISSION, aid)));
        }
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(permissions, role.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permissions);
    }

    @Override
    public String toString() {
        Role role = new Role((RTable) BMDB.SelectById(Constant.T_ROLE, id));
        return "{\"Role\":{" +
                "\"id\":\"" + id +
                "\", \"name\":\"" + name +
                "\", \"permissions\":" + role.getPermissions() +
                "}}";
    }
}
