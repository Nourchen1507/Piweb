package com.example.myproject.Repository;

import com.example.myproject.entities.Don;
import com.example.myproject.entities.Post;
import com.example.myproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository  extends JpaRepository<Post,Long> {


 List<Post> findByNomContainingIgnoreCase (String nom)
 ;


    List<Post> findAllByUserId(Long id);



}
