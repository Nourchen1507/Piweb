 package com.example.myproject.Service;

import com.example.myproject.Exception.UserNotFoundException;
import com.example.myproject.Service.Twilio.SmsServiceImpl;
import com.example.myproject.entities.*;
import com.example.myproject.repositories.PasswordResetTokenRepository;
import com.example.myproject.repositories.RoleRepository;
import com.example.myproject.repositories.UserRepository;
import com.example.myproject.util.UserCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);
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
        user.setPassword(getEncodedPassword(user.getPassword()));
        user.setIsverified(0);
        User saveduser=userDao.save(user);
        emailService.sendVerificationEmail(saveduser);
        return saveduser;
    }

    public User isVerified (String userName){

        User VerifiedUser = userDao.findByUserName(userName);
        int a=VerifiedUser.getIsverified();
        logger.error(String.valueOf(a));
        if (VerifiedUser != null){

            logger.error("iciiii");
            if (VerifiedUser.getIsverified() == 1) {
                logger.error("iciiiisssss");
                return VerifiedUser;
            }
            else{
                VerifiedUser=null;
            }
            }
          return VerifiedUser;
        }





    public User activateUser(String token) {
        User user = userDao.findByVerificationToken(token);
        if (user != null) {
            user.setIsverified(1); 
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
    public User findOne(Long id){
        return userDao.findById(id).orElse(null);
    }
    public List<User> getUnverifiedUsers() {
        return userDao.findUnverifiedUsers();
    }
    public void delete(Long id){
        User u= userDao.findById(id).orElse(null);
        u.getRole().clear();
        userDao.delete(u);
    }
    public void update(User user){
        userDao.save(user);
    }

    public long count(){
        long count=userDao.count();
        return count;
    }
    public long countoperateur(){
        long countoperateur=0;
        List<User> users=userDao.findAll();
        for(User user:users) {

            Set<Role> roles=user.getRole();
            Role role= roles.iterator().next();
            String rolename = role.getRoleName();
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
        int  verified=1;
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setIsverified(verified);
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

    public void editUser(User user){
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



}
