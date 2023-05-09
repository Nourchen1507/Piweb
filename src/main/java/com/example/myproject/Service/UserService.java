package com.example.myproject.Service;

import com.example.myproject.Exception.UserNotFoundException;
import com.example.myproject.Service.Twilio.SmsServiceImpl;
import com.example.myproject.entities.*;
import com.example.myproject.repositories.PasswordResetTokenRepository;
import com.example.myproject.repositories.RoleRepository;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.request.SingupRequest;
import com.example.myproject.util.UserCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    @Autowired
    private UserRepository userDao;

    @Autowired
    private RoleRepository roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender userMailSender;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private SmsServiceImpl smsService;

    public void initRoleAndUser() {


        Role adminRole = new Role();
        adminRole.setRoleName("Admin");

        roleDao.save(adminRole);

        Role OrganisationRole = new Role();
        OrganisationRole.setRoleName("Organisation");

        roleDao.save(OrganisationRole);

        Role userRole = new Role();
        userRole.setRoleName("helper");

        roleDao.save(userRole);


    }

    public User registerNewUser(User user) {
        logger.info("aaaaa");
logger.info(user.getRole().getRoleName());
        Role role = roleDao.findById(user.getRole().getRoleName()).get();
        user.setRole(role);
        user.setPassword(getEncodedPassword(user.getPassword()));
        user.setVerified(false);
        User saveduser=userDao.save(user);
        emailService.sendVerificationEmail(saveduser);
        return saveduser;
    }

    public User isVerified (String userName){

        User VerifiedUser = userDao.findByUserName(userName);
        boolean a=VerifiedUser.isVerified();
        logger.error(String.valueOf(a));


            logger.error("iciiii");
            if (VerifiedUser.isVerified() ) {
                logger.error("iciiiisssss");
                return VerifiedUser;
            }
            else{
                VerifiedUser=null;
            }

          return VerifiedUser;
        }





    public User activateUser(String token) {
        User user = userDao.findByVerificationToken(token);
        if (user != null) {
            user.setVerified(true);
            user.setVerificationToken(null);
            userDao.save(user);
        }
        return user;
    }




    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public List<User> getAll(){
        return userDao.findAll();
    }
    public User findOne(String userName){
        return userDao.findByUserName(userName);
    }
    public List<User> getUnverifiedUsers() {
        return userDao.findUnverifiedUsers();
    }
    public void delete(String userName){
        User u= userDao.findByUserName(userName);
        userDao.delete(u);
    }
    public User update(Long id, User user) throws IOException {
        User user2 = userDao.findByIdUser(id);
        user2.setMailAddress(user.getMailAddress());
        user2.setImageProfile(user.getImageProfile());
        user2.setUserPhone(user.getUserPhone());
        user2.setLocation(user.getLocation());
        user2.setCertificate(user.getCertificate());
        user2.setUserName(user.getUserName());
        return userDao.save(user2);
    }

    public long count(){
        long count=userDao.count();
        return count;
    }
    public long countoperateur(){
        long countoperateur=0;
        List<User> users=userDao.findAll();
        for(User user:users) {



            String rolename = user.getRole().getRoleName();
            if(rolename.equals("helper")){
                countoperateur+=1;
            }
        }
        return countoperateur;
    }

    public User findByMailAdress(String mailAddress) {
        return userDao.findByMailAddress(mailAddress);
    }

    @Value("${spring.mail.username}")
    private String fromAddress;
    public void generatePasswordResetToken(String mailAddress) {
        User user = userDao.findByMailAddress(mailAddress);
        if (user == null) {
            throw new UserNotFoundException("No user found with Email: " + mailAddress);
        }

        String token = generateToken();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));

        passwordResetTokenRepository.save(passwordResetToken);

        String subject = "Password reset request";
        String text = "Please click the following link to reset your password: http://localhost:8081/forgot-password?token=" + token;
        emailService.sendEmail(user.getMailAddress(), subject, text);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void changePassword(String userName, String newPassword) {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }
    public void ISVerified(String userName) {

        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setVerified(true);
        userDao.save(user);
    }



    public boolean ifEmailExist(String mailAddress){
        return userDao.existsByMailAddress(mailAddress);
    }

    @Transactional
    public String getPasswordByMailAddress(String mailAddress){
        return userDao.getPasswordByEmail(mailAddress);
    }

    public User findByMailAddress(String mailAddress)
    {
        return this.userDao.findByMailAddress(mailAddress);
    }

    public void editUser(@Valid @RequestBody User user){
        this.userDao.save(user);
    }

    //reset password Using SMS:
    public User findByPhone(String phone)
    {
        return this.userDao.findByUserPhone(phone);
    }

    //Check Phone number in DB and send 6 digits code.

    public UserAccountResponse CheckSMS (UserResetPasswordSMS userResetPasswordSMS) {
        // Retrieve user using the entered phone number.
        User user = this.findByPhone(userResetPasswordSMS.getPhone());
        System.out.println("la variable User est " + user);
        System.out.println("la variable Phone est " + userResetPasswordSMS.getPhone());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if (user != null) {
            String code = UserCode.getSmsCode();
            System.out.println("le code est" + code);
            this.smsService.SendSMS(userResetPasswordSMS.getPhone(),code);
            System.out.println("la variable User est" + user);
            user.setUserCode(code);
            userDao.save(user);
            accountResponse.setResult(1);
        } else {
            accountResponse.setResult(0);
        }
        return accountResponse;
    }



    //Compare given code with the one stored in DB and reset password.
    public UserAccountResponse resetPasswordSMS(UserNewPasswordSMS userNewPasswordSMS) {
        User user = this.findByPhone(userNewPasswordSMS.getPhone());
        UserAccountResponse accountResponse = new UserAccountResponse();
        if(user != null){
            if(user.getUserCode().equals(userNewPasswordSMS.getCode())){
                user.setPassword(passwordEncoder.encode(userNewPasswordSMS.getPassword()));
                user.setUserCode("expired");
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


    public Integer changePassword(Long id, ChangePasswordRequest password) {
        User user = userDao.findByIdUser(id);
        if (user == null) {
            return 404;
        }
        logger.info(password.getNewpassword());
        if(passwordEncoder.matches(password.getOldpassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(password.getNewpassword()));
            userDao.save(user);
            return 200;
        }
        else {
            return 400;
        }
    }

    public List < User > findAll() {
        return userDao.findAll();
    }

}
