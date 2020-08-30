package com.jnshu.pojo;

public class Account {

    private Long id;
    private String username;
    private String password;
    private Long createat;
    private String createby;
    private Long updateat;
    private String updateby;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createat=" + createat +
                ", createby='" + createby + '\'' +
                ", updateat=" + updateat +
                ", updateby='" + updateby + '\'' +
                '}';
    }

    public Account(Long id, String username, String password, Long createat, String createby, Long updateat, String updateby) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createat = createat;
        this.createby = createby;
        this.updateat = updateat;
        this.updateby = updateby;
    }

    public Account() {

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

    public Long getCreateat() {
        return createat;
    }

    public void setCreateat(Long createat) {
        this.createat = createat;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Long getUpdateat() {
        return updateat;
    }

    public void setUpdateat(Long updateat) {
        this.updateat = updateat;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public Account(Long id) {
        this.id = id;
    }
}
