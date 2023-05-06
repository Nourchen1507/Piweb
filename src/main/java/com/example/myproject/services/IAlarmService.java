package com.example.myproject.services;

import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Event;

import java.util.List;

public interface IAlarmService  {

    Alarm addAlarm(Alarm alarm);

    Alarm updateAlarm(Long idAlarm, Alarm alarm);

    void removeAlarm(Long idAlarm);

    List<Alarm> retrieveAlarm(Long idAlarm);

    List<Alarm> getAllAlarm();
}
