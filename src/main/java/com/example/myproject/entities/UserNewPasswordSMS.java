package com.example.myproject.entities;

import lombok.Data;

@Data
public class UserNewPasswordSMS {
    private String phone;
    private String code;
    private String password;
}


