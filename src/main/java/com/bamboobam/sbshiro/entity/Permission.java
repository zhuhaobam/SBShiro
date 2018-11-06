package com.bamboobam.sbshiro.entity;

public class Permission {

    private Long id;
    private String permissionname;
    private String url;

    public Permission() {
    }

    public Permission(Permission permission) {
        this.id = permission.id;
        this.permissionname = permission.permissionname;
        this.url = permission.url;
    }

    public Permission(Long id, String permissionname, String url) {
        this.id = id;
        this.permissionname = permissionname;
        this.url = url;
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
    public String toString() {
        return "{\"Permission\":{" +
                "\"id\":\"" + id +
                "\", \"permissionname\":\"" + permissionname +
                "\", \"url\":\"" + url +
                "\"}}";
    }
}
