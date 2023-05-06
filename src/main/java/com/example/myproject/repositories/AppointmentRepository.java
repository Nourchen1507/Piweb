package com.example.myproject.repositories;

import com.example.myproject.dto.MonthlyScheduledAppointmentCountDTO;
import com.example.myproject.dto.UserAppointmentCountDTO;
import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByOrganizationAndDate(User organization, LocalDateTime date);
    List<Appointment> findByHelperAndDate(User helper, LocalDateTime date);

    long countByDateBetween(LocalDateTime atStartOfDay, LocalDateTime plusDays);

    long countByHelper(User helper);

    long countByOrganization(User organization);

    List<Appointment> findAllByHelper_IdUserOrderByIdDesc(Long id);


    @Query(value = "SELECT new com.example.myproject.dto.MonthlyScheduledAppointmentCountDTO(EXTRACT(year from appointment.date),EXTRACT(month from appointment.date),count(appointment)) " +
            "FROM Appointment appointment " +
            "WHERE appointment.date >= :start AND appointment.date <= :end " +
            "GROUP BY EXTRACT(year from appointment.date), EXTRACT(month from appointment.date) " +
            "ORDER BY EXTRACT(year from appointment.date), EXTRACT(month from appointment.date)")
    List<MonthlyScheduledAppointmentCountDTO> getMonthlyScheduledAppointmentCount(@Param("start") LocalDateTime start,
                                                                                  @Param("end") LocalDateTime end);
}
