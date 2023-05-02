package com.example.myproject.services;

import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Event;
import com.example.myproject.repository.AlarmRepository;
import com.example.myproject.repository.EventRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("alarm")
public class AlarmServiceImpl implements IAlarmService{


 final AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl (AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }


    @Override
    public void addAlarm(Alarm alarm) {

        alarmRepository.save(alarm);


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
}
