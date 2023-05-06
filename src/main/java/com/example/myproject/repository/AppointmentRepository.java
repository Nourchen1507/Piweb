package com.example.myproject.repository;

import com.example.myproject.entities.Alarm;
import com.example.myproject.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAppointmentByDateBetween(LocalDateTime now, LocalDateTime plusDays);
}
