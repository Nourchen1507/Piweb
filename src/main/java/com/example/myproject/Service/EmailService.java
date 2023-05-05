package com.example.myproject.Service;

import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import com.example.myproject.entities.UserMail;
import com.example.myproject.repositories.IUserEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService implements IUserEmailRepository {


    @Autowired UserService userservice;
    @Autowired
    private JavaMailSender userMailSender;
    @Autowired
    private VerificationTokenService verificationTokenService;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        userMailSender.send(message);
    }
    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getMailAddress());
        user.setVerificationToken(verificationTokenService.generateVerificationToken());
        message.setSubject("Vérification du compte");
        message.setText("Bonjour " + user.getUserName() + ",\n\n" +
                "Veuillez cliquer sur le lien ci-dessous pour activer votre compte :\n\n" +
                "http://localhost:4200/activate?token=" + user.getVerificationToken());
        userMailSender.send(message);
    }
@Override
    public void sendCodeByMail(UserMail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("wledelkhirr@gmail.com");
        simpleMailMessage.setTo(mail.getTo());
        simpleMailMessage.setSubject("Code Active");
        simpleMailMessage.setText(mail.getCode());
        userMailSender.send(simpleMailMessage);
    }
    public void sendAppointmentCreatedEmail(User helper, User organization, Appointment appointment)
            throws MessagingException {
        MimeMessage message = userMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setSubject("New appointment created");
        mimeMessageHelper.setTo(new String[]{helper.getMailAddress(), organization.getMailAddress()});
        mimeMessageHelper.setText("A new appointment has been created for " + appointment.getDate() + " at " + appointment.getLieu() + ".");
        mimeMessageHelper.setFrom("wledelkhirr@gmail.com");
        userMailSender.send(message);
    }




}
