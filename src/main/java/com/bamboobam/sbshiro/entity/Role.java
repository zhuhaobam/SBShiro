package com.bamboobam.sbshiro.entity;

import java.util.List;

public class Role {

    private Long id;
    private String name;
    private List<Permission> permissions;

    public Role() { }

    public Role(Role role) {
        this.id = role.id;
        this.name = role.name;
        this.permissions = role.permissions;
    }

    public Role(Long id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "{\"Role\":{" +
                "\"id\":\"" + id +
                "\", \"name\":\"" + name +
                "\", \"permissions\":" + permissions +
                "}}";
    }
}
