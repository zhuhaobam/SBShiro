package com.bamboobam.sbshiro.config.repositoryconfig.table;

public class PTable {

    private Long id;
    private String permissionname;
    private String url;

    public PTable() {
    }

    public PTable(Long id, String permissionname, String url) {
        this.id = id;
        this.permissionname = permissionname;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public String getUrl() {
        return url;
    }
}
