package com.example.myproject.controller;


import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Event;
import com.example.myproject.services.IAlarmService;
import com.example.myproject.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
@RequestMapping("/alarm")
public class AlarmController  {




    @Autowired
    private IAlarmService iAlarmService;

    @PostMapping("/add")
    void addAlarm(@RequestBody Alarm alarm){
        iAlarmService.addAlarm(alarm);
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
