package com.example.myproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entities.Like;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long>{

}
