package com.example.myproject.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String mailAddress;

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
