package com.hncy.platform.authorizationserver.domain;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User implements Serializable{

    @Id
    private Integer id;

    @Version
    private Integer version;

    @NotEmpty
    private String username;
    private String password;
    private String fullname;
    private String roles;
    private String app;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}