package com.bamboobam.sbshiro.entity;

import com.bamboobam.sbshiro.config.repositoryconfig.table.PTable;

import java.util.Objects;

public class Permission {

    private Long id;
    private String permissionname;
    private String url;

    public Permission() {
    }

    public Permission(PTable pTable) {
        this.id = pTable.getId();
        this.permissionname = pTable.getPermissionname();
        this.url = pTable.getUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(permissionname, that.permissionname) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionname, url);
    }

    @Override
    public String toString() {
        return "{\"Permission\":{" +
                "\"id\":\"" + id +
                "\", \"permissionname\":\"" + permissionname +
                "\", \"url\":\"" + url +
                "\"}}";
    }
}
