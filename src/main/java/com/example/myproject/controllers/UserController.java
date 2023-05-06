package com.example.myproject.controllers;

import com.example.myproject.Service.EmailService;
import com.example.myproject.Service.UserService;
import com.example.myproject.Service.VerificationTokenService;
import com.example.myproject.entities.*;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.util.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenService verificationTokenService;

    @PostMapping("/registerNewUser")
    public User createUser(@RequestBody User user) {
        User savedUser = userService.registerNewUser(user);

        UserVerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);
        return savedUser;
    }

    @PutMapping("/activate/{verificationToken}")
    public ResponseEntity activateAccount(@PathVariable String verificationToken) {
        User user = userService.activateUser(verificationToken);
        if (user != null) {
            String to = user.getMailAddress();
            String subject = "Account Created";
            String text = "Your account has been created successfully.";
            emailService.sendEmail(to, subject, text);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }


    @GetMapping({"/forOrganisation"})
    @PreAuthorize("hasRole('Organisation')")
    public String forOrganisation(){
        return "This URL is only accessible to the Organisation";
    }

    @GetMapping({"/users"})
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping({"/user/{id}"})
    public User findOne(@PathVariable Long id){
        return userService.findOne(id);
    }

    @GetMapping("/unverified-users")
    @PreAuthorize("hasRole('Admin')")
    public List<User> getUnverifiedUsers() {
        return  userService.getUnverifiedUsers();
    }

    @GetMapping({"/forHelper"})
    @PreAuthorize("hasRole('helper')")
    public String forHelper(){
        return "This URL is only accessible to the helper";
    }

    @DeleteMapping ({"/delete/{Long}"})
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping  ({"/update"})
    @PreAuthorize("hasRole('Admin')")
    public void update(@RequestBody User user){
        userService.update(user);
    }
    @GetMapping("/count")
    @PreAuthorize("hasRole('Admin')")
    public long count(){return userService.count();}
    @GetMapping("/countoperateur")
    @PreAuthorize("hasRole('Admin')")
    public long countoperateur(){return userService.countoperateur();}

    @PutMapping("/change-password/{userName}/{newPassword}")
    public ResponseEntity<Void> changePassword(@PathVariable String userName, @PathVariable String newPassword) {
        userService.changePassword(userName, newPassword);
        return ResponseEntity.ok().build();
    }



    @PutMapping("/VerifUser/{userName}")
    @PreAuthorize("hasRole('Organisation')")
    public void VerifUser(@PathVariable String userName) {

        userService.ISVerified(userName);
    }


    // http://localhost:8080/checkEmail
    @PostMapping("/checkEmail")
    public UserAccountResponse resetPasswordEmail(@RequestBody UserResetPassword resetPassword){
        User user = this.userService.findByMailAddress(resetPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if(user != null){
            String code = UserCode.getCode();
            System.out.println("le code est" + code);
            UserMail mail = new UserMail(resetPassword.getEmail(),code);
            System.out.println("le mail est" + resetPassword.getEmail());
            System.out.println("la variable mail est" + mail);
            emailService.sendCodeByMail(mail);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            userDao.save(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }

    // http://localhost:8080/resetPassword
    @PostMapping("/resetPassword")
    public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword){
        User user = this.userService.findByMailAddress(newPassword.getEmail());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if(user != null){
            if(user.getUserCode().equals(newPassword.getCode())){
                user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
                userDao.save(user);
                accountResponse.setResult(1);
            } else {
                accountResponse.setResult(0);
            }
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }


    @PostMapping("/checkSMS")
    public UserAccountResponse CheckSMS (@RequestBody UserResetPasswordSMS userResetPasswordSMS) {
        return userService.CheckSMS(userResetPasswordSMS);
    }
    @PostMapping("/resetPasswordSMS")
    public UserAccountResponse resetPasswordSMS (@RequestBody UserNewPasswordSMS newPassword) {
        return userService.resetPasswordSMS(newPassword);
    }





}