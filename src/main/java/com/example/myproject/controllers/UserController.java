package com.example.myproject.controllers;

import com.example.myproject.Service.EmailService;
import com.example.myproject.Service.UserService;
import com.example.myproject.Service.VerificationTokenService;
import com.example.myproject.entities.*;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.request.SingupRequest;
import com.example.myproject.util.UserCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
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
    public ResponseType createUser( @RequestBody User user) {
    	System.out.println("useeeeeeeeeeer"+user);
        if (userDao.existsByUserName(user.getUserName())) {
            return new ResponseType(400);
        }

        if (userDao.existsByMailAddress(user.getMailAddress())) {
            return new ResponseType(404);
        }
        User savedUser = userService.registerNewUser(user);
        UserVerificationToken verificationToken = verificationTokenService.createVerificationToken(user); // création du jeton de vérification
        verificationTokenService.saveVerificationToken(verificationToken);
        return new ResponseType(200);
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
    @GetMapping({"/user/{userName}"})
    public User findOne(@PathVariable String userName){
        return userService.findOne(userName);
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

    @DeleteMapping ({"/delete/{id}"})
   // @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws IOException {

        User updatedUser = userService.update(id,user);

        return ResponseEntity.ok(updatedUser);
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


    @PostMapping("/checkEmail")
    public UserAccountResponse resetPasswordEmail(@RequestBody UserResetPassword resetPassword){
        log.info("hhhhh");
        log.info(resetPassword.getMailAddress());
        User user = this.userService.findByMailAddress(resetPassword.getMailAddress());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if(user != null){
            String code = UserCode.getCode();
            System.out.println("le code est" + code);
            UserMail mail = new UserMail(resetPassword.getMailAddress(),code);
            System.out.println("le mail est" + resetPassword.getMailAddress());
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

    @PostMapping("/resetPassword")
    public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword){
        User user = this.userService.findByMailAddress(newPassword.getMailAddress());
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

    @PutMapping("/changepassword/{id}")
    public ResponseType changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest password) {
        Integer code = userService.changePassword(id, password);
        return new ResponseType(code);
    }

    @GetMapping ("findbymail/{email}")
    public User UserExist(@PathVariable String email) {
        return userDao.findByMailAddress(email);
    }

    @GetMapping ("getAll")
    public Iterable<User>getAllUsers() {
        return userService.findAll();
    }
}

