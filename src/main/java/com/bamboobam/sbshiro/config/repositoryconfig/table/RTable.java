package com.bamboobam.sbshiro.config.repositoryconfig.table;

public class RTable {

    private Long id;
    private String name;
    private Long[] permissions;
    public RTable(){}
    public RTable(Long id, String name, Long[] permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long[] getPermissions() {
        return permissions;
    }
}
