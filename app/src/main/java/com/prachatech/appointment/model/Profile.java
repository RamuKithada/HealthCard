package com.prachatech.appointment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("mobile")
    @Expose
    String mobile;
    @SerializedName("email")
    @Expose
    String mail;
    @SerializedName("a_u_id")
    @Expose
    private String aUId;

    public String getaUId() {
        return aUId;
    }

    public void setaUId(String aUId) {
        this.aUId = aUId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
