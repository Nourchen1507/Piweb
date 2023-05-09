package com.example.myproject.Service;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.dto.MonthlyScheduledAppointmentCountDTO;
import com.example.myproject.dto.UserAppointmentCountDTO;
import com.example.myproject.dto.UserDTO;
import com.example.myproject.entities.Appointment;
import com.example.myproject.entities.User;
import com.example.myproject.repositories.AppointmentRepository;
import com.example.myproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final String ORGANIZATION_ROLE_NAME = "organisation";
    private static final String HELPER_ROLE_NAME = "helper";

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
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
    public List<AppointmentDTO> getAllAppointmentsForHelper(Long id) {
        return appointmentRepository
                .findAllByHelper_IdOrderByIdDesc(id)
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
        appointmentDTO.setOrganization(mapUser(appointment.getOrganization()));
        appointmentDTO.setHelper(mapUser(appointment.getHelper()));
        return appointmentDTO;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The appointment is not available");
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

        return toAppointmentDTO(appointment);
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

    @Override
    public long getTotalAppointments() {
        return appointmentRepository.count();
    }

    @Override
    public long getDailyAppointments(LocalDate date) {
        return appointmentRepository.countByDateBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1));
    }

    @Override
    public long getWeeklyAppointments(LocalDate date) {
        return appointmentRepository.countByDateBetween(date.with(DayOfWeek.MONDAY).atStartOfDay(), date.with(DayOfWeek.SUNDAY).atStartOfDay().plusDays(1));
    }

    @Override
    public long getMonthlyAppointments(LocalDate date) {
        return appointmentRepository.countByDateBetween(date.withDayOfMonth(1).atStartOfDay(), date.withDayOfMonth(date.lengthOfMonth()).atStartOfDay().plusDays(1));
    }

    @Override
    public List<UserAppointmentCountDTO> getAppointmentsByHelper() {
        return getUserAppointmentCountDTOS(HELPER_ROLE_NAME);
    }

    @Override
    public List<UserAppointmentCountDTO> getAppointmentsByOrganization() {

        return getUserAppointmentCountDTOS(ORGANIZATION_ROLE_NAME);
    }

    @Override
    public List<MonthlyScheduledAppointmentCountDTO> getScheduledAppointmentsCount() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plus(1, ChronoUnit.YEARS);
        return appointmentRepository.getMonthlyScheduledAppointmentCount(start, end);
    }

    @Override
    public List<UserDTO> getAllOrganizations() {
        return userRepository
                .findAllByRole_RoleNameOrderByUserName(ORGANIZATION_ROLE_NAME)
                .stream()
                .map(this::mapUser)
                .collect(Collectors.toList());
    }

    private UserDTO mapUser(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setRole(user.getRole().getRoleName());
        return userDTO;
    }

    private User loadUser(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    private boolean isOrganizationAvailable(User organization, LocalDateTime appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByOrganizationAndDate(organization, appointmentDate);
        return appointments.isEmpty();
    }

    private boolean isHelperAvailable(User helper, LocalDateTime appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByHelperAndDate(helper, appointmentDate);
        return appointments.isEmpty();
    }

    private List<UserAppointmentCountDTO> getUserAppointmentCountDTOS(String roleName) {
        List<User> users = userRepository.findAllByRole_RoleNameOrderByUserName(roleName);
        List<UserAppointmentCountDTO> result = new ArrayList<>();
        for (User helper : users) {
            UserAppointmentCountDTO userAppointmentCountDTO = new UserAppointmentCountDTO();
            userAppointmentCountDTO.setUserId(helper.getId());
            userAppointmentCountDTO.setNumberOfAppointments(appointmentRepository.countByOrganization(helper));
            result.add(userAppointmentCountDTO);

        }
        return result;
    }
}

