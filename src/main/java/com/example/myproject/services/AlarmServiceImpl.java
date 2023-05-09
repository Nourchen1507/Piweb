package com.example.myproject.services;

import com.example.myproject.entities.Alarm;  
import com.example.myproject.entities.Appointment;
import com.example.myproject.repositories.AppointmentRepository;
import com.example.myproject.repository.AlarmRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("alarm")
@EnableScheduling
@Configuration

public class AlarmServiceImpl implements IAlarmService{


 final AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl (AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }


    @Override
    public Alarm addAlarm(Alarm alarm) {

       return alarmRepository.save(alarm);



    }

    @Override
    public Alarm updateAlarm(Long idAlarm, Alarm alarm) {


        List<Alarm> newAlarm = alarmRepository.findByIdAlarm(idAlarm);
        if (alarm.getName()!= null)
            newAlarm.get(0).setName(alarm.getName());
        return alarmRepository.save(newAlarm.get(0));
    }

    @Override
    public void removeAlarm(Long idAlarm) {
        alarmRepository.deleteById(idAlarm);


    }

    @Override
    public List<Alarm> retrieveAlarm(Long idAlarm) {
        return alarmRepository.findByIdAlarm(idAlarm);
    }

    @Override
    public List<Alarm> getAllAlarm() {
        List<Alarm> alarms = new ArrayList<>();
        alarmRepository.findAll().forEach(alarms::add);
        return alarms;
    }



    @Service
    public class AlarmeService {

        @Autowired
        private JavaMailSender mailSender;

        @Autowired
        private AppointmentRepository appointmentRepository;

    	@Scheduled(cron = "0 * * * * *") // every midnight
        public void sendReminderEmails() {
            List<Appointment> appointments = appointmentRepository.findAll();
            for (Appointment appointment:appointments
                 ) {
                if(appointment.getDate().toLocalDate().equals(LocalDate.now()) && appointment.getAlarm()!=null){
                    sendEmail(appointment.getHelper().getMailAddress(), "Reminder", "Hello, this is a gentle reminder for your appointment which will be tomorrow.");
                    alarmRepository.delete(appointment.getAlarm());
                }
            }
            String subject = "Rappel d'appointement";
            String body = "Bonjour";
          //  sendEmail("kefiskander99@gmail.com", subject, body);
        }


        private void sendEmail(String recipientEmail, String subject, String body) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }

        // méthode pour créer un appointement et déclencher l'envoi d'un e-mail de rappel
        public void createAppointement(Appointment appointment) {
            appointmentRepository.save(appointment);
            // déclencher l'envoi d'un e-mail de rappel 24 heures avant l'appointement
            LocalDateTime dateTimeBeforeAppointement = appointment.getDate().minusDays(1);
            scheduleEmailReminder(dateTimeBeforeAppointement, appointment);
        }

        // méthode pour planifier l'envoi d'un e-mail de rappel
        private void scheduleEmailReminder(LocalDateTime dateTimeBeforeAppointement, Appointment appointment) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(appointment.getHelper().getMailAddress());
            message.setSubject("Rappel d'appointement");
            message.setText("Bonjour,\n\nVous avez un appointement prévu demain à " + appointment.getDate() + ".\n\nCordialement,\nL'équipe de notre entreprise");
            Date date = Date.from(dateTimeBeforeAppointement.atZone(ZoneId.systemDefault()).toInstant());
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mailSender.send(message);
                    timer.cancel();
                }
            }, date);
        }
    }



}
