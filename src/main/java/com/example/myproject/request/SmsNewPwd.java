package com.example.myproject.request;


import lombok.Data;

@Data

public class SmsNewPwd {
    private String userPhone;
    private String userCode;
    private String password;
}