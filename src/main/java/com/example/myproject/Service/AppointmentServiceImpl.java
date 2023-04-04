package com.example.myproject.services;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.myproject.repository.AppointmentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;



    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository
                .findAll()
                .stream()
                .map(this::toAppointmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {

        return appointmentRepository.findById(id)
                .map(this::toAppointmentDTO)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + id + " not found"));
    }

    @Override
    public AppointmentDTO createAppointment(CreateUpdateAppointmentDTO createUpdateAppointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setLieu(createUpdateAppointmentDTO.getLieu());
        appointment.setDate(createUpdateAppointmentDTO.getDate());
        appointment.setHelper(loadUser(createUpdateAppointmentDTO.getHelperId()));
        appointment.setOrganization(loadUser(createUpdateAppointmentDTO.getOrganizationId()));
        return toAppointmentDTO( appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, CreateUpdateAppointmentDTO createUpdateAppointmentDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with id " + id + " not found"));
        appointment.setLieu(createUpdateAppointmentDTO.getLieu());
        appointment.setDate(createUpdateAppointmentDTO.getDate());
        appointment.setHelper(loadUser(createUpdateAppointmentDTO.getHelperId()));
        appointment.setOrganization(loadUser(createUpdateAppointmentDTO.getOrganizationId()));
        return toAppointmentDTO( appointmentRepository.save(appointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    private AppointmentDTO toAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setLieu(appointment.getLieu());
        appointmentDTO.setDate(appointment.getDate());
        appointmentDTO.setOrganizationId(
                appointment.getOrganization() != null ?
                        appointment.getOrganization().getId() : null);
        appointmentDTO.setHelperId(
                appointment.getHelper() != null ?
                        appointment.getHelper().getId() : null);
        return appointmentDTO;
    }

    private User loadUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }
}

