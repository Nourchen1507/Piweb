package com.example.myproject.entities;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    @NotBlank
    private String newpassword;
    @NotBlank
    private String oldpassword;

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }
}
