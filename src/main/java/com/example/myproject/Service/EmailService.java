package com.example.myproject.Service;

import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;



    public void sendAppointmentCreatedEmail(User helper, User organization, Appointment appointment)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setSubject("New appointment created");
        mimeMessageHelper.setTo(new String[]{helper.getMailAddress(), organization.getMailAddress()});
        mimeMessageHelper.setText("A new appointment has been created for " + appointment.getDate() + " at " + appointment.getLieu() + ".");
        mimeMessageHelper.setFrom("wledelkhirr@gmail.com");
        javaMailSender.send(message);
    }
}

