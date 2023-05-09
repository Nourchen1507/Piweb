package com.example.myproject.repositories;

import com.example.myproject.entities.UserMail;


public interface IUserEmailRepository {
    public void sendCodeByMail(UserMail mail);
}