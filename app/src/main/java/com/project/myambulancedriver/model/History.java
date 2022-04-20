package com.project.myambulancedriver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("id_history")
    @Expose
    private Integer id_history;
    @SerializedName("no_ktp")
    @Expose
    private String no_ktp;
    @SerializedName("id_loc_user")
    @Expose
    private String id_loc_user;
    @SerializedName("id_driver")
    @Expose
    private String id_driver;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("no_telpon")
    @Expose
    private String no_telpon;
    @SerializedName("no_kk")
    @Expose
    private String no_kk;
    @SerializedName("no_plat")
    @Expose
    private String no_plat;
    @SerializedName("alamat")
    @Expose
    private String alamat;

    public History(Integer id_history, String no_ktp, String id_loc_user, String id_driver, String username,
                   String password, String no_telpon, String no_kk, String no_plat, String alamat) {
        this.id_history = id_history;
        this.no_ktp = no_ktp;
        this.id_loc_user = id_loc_user;
        this.id_driver = id_driver;
        this.username = username;
        this.password = password;
        this.no_telpon = no_telpon;
        this.no_kk = no_kk;
        this.no_plat = no_plat;
        this.alamat = alamat;
    }

    public Integer getId_history() {
        return id_history;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public String getId_loc_user() {
        return id_loc_user;
    }

    public String getId_driver() {
        return id_driver;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public String getAlamat() {
        return alamat;
    }
}
