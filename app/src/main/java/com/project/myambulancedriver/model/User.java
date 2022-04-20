package com.project.myambulancedriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id_driver")
    @Expose
    private String id_driver;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("no_plat")
    @Expose
    private String no_plat;

    public User(String id_driver, String username, String password, String no_plat) {
        this.id_driver = id_driver;
        this.username = username;
        this.password = password;
        this.no_plat = no_plat;
    }

    public String getId_driver() {
        return id_driver;
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

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }
}
