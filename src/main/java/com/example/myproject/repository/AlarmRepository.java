package com.example.myproject.repository;

import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlarmRepository extends JpaRepository<Alarm,Long> {
    List<Alarm> findByIdAlarm(Long idAlarm);
}
