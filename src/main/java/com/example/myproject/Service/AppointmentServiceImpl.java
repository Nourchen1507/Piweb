package com.example.myproject.Service;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import com.example.myproject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.myproject.Repository.AppointmentRepository;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements com.example.myproject.Service.AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final com.example.myproject.Repository.UserRepository userRepository;
    private final EmailService emailService;


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

    /*@Override
    public AppointmentDTO createAppointment(CreateUpdateAppointmentDTO createUpdateAppointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setLieu(createUpdateAppointmentDTO.getLieu());
        appointment.setDate(createUpdateAppointmentDTO.getDate());
        appointment.setHelper(loadUser(createUpdateAppointmentDTO.getHelperId()));
        appointment.setOrganization(loadUser(createUpdateAppointmentDTO.getOrganizationId()));
        return toAppointmentDTO( appointmentRepository.save(appointment));
    }*/

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


    @Override
    public AppointmentDTO createAppointment(CreateUpdateAppointmentDTO appointmentDto) {
        User helper = userRepository.findById(appointmentDto.getHelperId())
                .orElseThrow(() -> new EntityNotFoundException("Helper not found"));
        User organization = userRepository.findById(appointmentDto.getOrganizationId())
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
        LocalDateTime appointmentDate = appointmentDto.getDate();
        String appointmentLieu = appointmentDto.getLieu();

        // Check availability of organization and helper
        boolean isOrganizationAvailable = isOrganizationAvailable(organization, appointmentDate);
        boolean isHelperAvailable = isHelperAvailable(helper, appointmentDate);

        if (!isOrganizationAvailable || !isHelperAvailable) {
            throw new EntityNotFoundException("The appointment is not available");
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setHelper(helper);
        appointment.setOrganization(organization);
        appointment.setDate(appointmentDate);
        appointment.setLieu(appointmentLieu);

        appointmentRepository.save(appointment);

        try {
            emailService.sendAppointmentCreatedEmail(helper, organization, appointment);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return mapToDto(appointment);
    }

    private boolean isOrganizationAvailable(User organization, LocalDateTime appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByOrganizationAndDate(organization, appointmentDate);
        return appointments.isEmpty();
    }

    private boolean isHelperAvailable(User helper, LocalDateTime appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByHelperAndDate(helper, appointmentDate);
        return appointments.isEmpty();
    }

    private AppointmentDTO mapToDto(Appointment appointment) {
        AppointmentDTO appointmentDto = new AppointmentDTO();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setLieu(appointment.getLieu());
        appointmentDto.setHelperId(appointment.getHelper().getId());
        appointmentDto.setOrganizationId(appointment.getOrganization().getId());

        return appointmentDto;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByOrganizationAndDate(User organization, LocalDateTime date) {
        List<Appointment> appointments = appointmentRepository.findByOrganizationAndDate(organization, date);
        return appointments.stream()
                .map(this::toAppointmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByHelperAndDate(User helper, LocalDateTime date) {
        List<Appointment> appointments = appointmentRepository.findByHelperAndDate(helper, date);
        return appointments.stream()
                .map(this::toAppointmentDTO)
                .collect(Collectors.toList());
    }

}

