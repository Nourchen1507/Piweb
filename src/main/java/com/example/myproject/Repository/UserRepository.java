package com.example.myproject.Repository;

import com.example.myproject.entities.Post;
import com.example.myproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
