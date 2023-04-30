package com.example.myproject.Repository;

import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByOrganizationAndDate(User organization, LocalDateTime date);
    List<Appointment> findByHelperAndDate(User helper, LocalDateTime date);
}
