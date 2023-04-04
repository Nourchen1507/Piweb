package com.example.myproject.services;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> getAllAppointments();

    AppointmentDTO getAppointmentById(Long id) ;

    AppointmentDTO createAppointment(CreateUpdateAppointmentDTO appointment) ;

    AppointmentDTO updateAppointment(Long id, CreateUpdateAppointmentDTO updatedAppointment);

    void deleteAppointment(Long id);
}
