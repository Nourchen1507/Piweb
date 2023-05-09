package com.example.myproject.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResetPassword {
    private String mailAddress;

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
