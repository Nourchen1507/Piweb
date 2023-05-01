package com.example.myproject.Service;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.dto.UserAppointmentCountDTO;
import com.example.myproject.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO getAppointmentById(Long id) ;

    //AppointmentDTO createAppointment(CreateUpdateAppointmentDTO appointment) ;

    AppointmentDTO updateAppointment(Long id, CreateUpdateAppointmentDTO updatedAppointment);

    void deleteAppointment(Long id);

    AppointmentDTO createAppointment(CreateUpdateAppointmentDTO appointmentDto);

    List<AppointmentDTO> getAppointmentsByHelperAndDate(User helper, LocalDateTime date);

    List<AppointmentDTO> getAppointmentsByOrganizationAndDate(User organization, LocalDateTime date);
    long getTotalAppointments();
    long getDailyAppointments(LocalDate date);
    long getWeeklyAppointments(LocalDate date);
    long getMonthlyAppointments(LocalDate date);
    List<UserAppointmentCountDTO> getAppointmentsByHelper();
    List<UserAppointmentCountDTO> getAppointmentsByOrganization();
}
