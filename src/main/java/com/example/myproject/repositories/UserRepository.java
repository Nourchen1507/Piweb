package com.example.myproject.repositories;

import com.example.myproject.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdUser(Long id);
    User findByUserName(String userName);
    User findByVerificationToken(String Token);


    Boolean existsByUserName(String userName);
    @Query("SELECT u FROM User u WHERE u.verified = false")
    List<User> findUnverifiedUsers();
    @Query("SELECT u FROM User u WHERE u.verified = true")
    List<User> findVerifiedUsers();


    public User findByMailAddress(String mailAddress);

    public boolean existsByMailAddress(String mailAddress);

    @Query("select u.password from User u where u.mailAddress=?1")
    public String getPasswordByEmail(String mailAddress);

    public User findByUserPhone(String phone);

}
