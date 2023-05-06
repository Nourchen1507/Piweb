package com.example.myproject.Repository;

import com.example.myproject.entities.Don;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonRepository extends JpaRepository<Don,Long> {


    List<Don> findByPostId(Long idPost);

    @Query("SELECT d FROM Don d WHERE d.post.user.id = :userId")
    List<Don> findDonsByUserId(@Param("userId") Long userId);

    List<Don> findByUserId(Long userId);
}
