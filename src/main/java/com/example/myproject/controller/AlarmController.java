package com.example.myproject.controller;


import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Appointment;
import com.example.myproject.repository.IAppointmentRepository;
import com.example.myproject.services.IAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/alarm")
public class AlarmController  {



    @Autowired
    private IAlarmService iAlarmService;

    @Autowired
    private IAppointmentRepository iAppointmentRepository;

    @PostMapping("/add/{id_appointment}")
    public ResponseEntity<Alarm> addAlarm(@PathVariable("id_appointment") Long idAppointment, @RequestBody Alarm alarm){
        Optional<Appointment> appointment = iAppointmentRepository.findById(idAppointment);
        System.out.println("appointme================="+appointment.get());
        if(appointment.isPresent()){
            alarm.setAppointment(appointment.get());
            iAlarmService.addAlarm(alarm);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(alarm);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }

    @DeleteMapping("/delete/{id}")
    void deleteAlarm(@PathVariable("id") Long idAlarm){
        iAlarmService.removeAlarm(idAlarm);
    }


    @PutMapping("/update/{id}")
    Alarm updateAlarm(@PathVariable("id") Long idAlarm , @RequestBody Alarm alarm){
        return  iAlarmService.updateAlarm(idAlarm,alarm);
    }

    @GetMapping("/get/{id}")
    List<Alarm> getAlarm(@PathVariable("id") Long idAlarm){
        return  iAlarmService.retrieveAlarm(idAlarm);
    }

    @GetMapping ("/all")
    List<Alarm> getAllAlarm(){
        return  iAlarmService.getAllAlarm();
    }

}
