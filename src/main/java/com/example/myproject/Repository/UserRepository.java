package com.example.myproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
