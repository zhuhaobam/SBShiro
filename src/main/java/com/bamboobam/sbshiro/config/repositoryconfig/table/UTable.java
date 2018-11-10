package com.bamboobam.sbshiro.config.repositoryconfig.table;

public class UTable {

    private Long id;
    private String username;
    private String password;
    private String salt;
    private Long[] roles;

    public UTable() {
    }

    public UTable(Long id, String username, String password, String salt, Long[] roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Long[] getRoles() {
        return roles;
    }
}
