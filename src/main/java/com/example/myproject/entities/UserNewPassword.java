package com.example.myproject.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNewPassword {
    private String mailAddress;
    private String code;
    private String password;
}
