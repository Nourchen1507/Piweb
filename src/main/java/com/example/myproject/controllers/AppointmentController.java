package com.example.myproject.controllers;

import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.dto.MonthlyScheduledAppointmentCountDTO;
import com.example.myproject.dto.UserAppointmentCountDTO;
import com.example.myproject.Service.AppointmentService;
import com.example.myproject.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;




    @GetMapping
    public List<AppointmentDTO> getAllAppointments() {
       return appointmentService.getAllAppointments();
    }

    @GetMapping("/helper/{id}")
    public List<AppointmentDTO> getAllAppointmentsForHelper(@PathVariable("id") Long id) {
        return appointmentService.getAllAppointmentsForHelper(id);
    }

    @GetMapping("/{id}")
    public AppointmentDTO getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody CreateUpdateAppointmentDTO appointment) {
    	System.out.println("appointmenttt"+appointment);
        return appointmentService.createAppointment(appointment);
    }

    @PutMapping("/{id}")
    public AppointmentDTO updateAppointment(@PathVariable Long id, @RequestBody CreateUpdateAppointmentDTO updatedAppointment) {
        return appointmentService.updateAppointment(id, updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/appointmentsByHelper")
    public List<UserAppointmentCountDTO> getAppointmentsByHelper(){
        return appointmentService.getAppointmentsByHelper();
    }
    @GetMapping("/appointmentsByOrganization")
    public List<UserAppointmentCountDTO> getAppointmentsByOrganization(){
        return appointmentService.getAppointmentsByOrganization();
    }
    @GetMapping("/getTotalAppointments")
    public long getTotalAppointments(){
        return appointmentService.getTotalAppointments();
    }

    @GetMapping("/getDailyAppointments")
    public long getDailyAppointments(@RequestParam("date") LocalDate date){
        return appointmentService.getDailyAppointments(date);
    }
    @GetMapping("/getWeeklyAppointments")
    public long getWeeklyAppointments(@RequestParam("date") LocalDate date){
        return appointmentService.getWeeklyAppointments(date);
    }
    @GetMapping("/getMonthlyAppointments")
    public long getMonthlyAppointments(@RequestParam("date") LocalDate date){
        return appointmentService.getMonthlyAppointments(date);
    }

    @GetMapping("/scheduledAppointmentsCount")
    public List<MonthlyScheduledAppointmentCountDTO> getScheduledAppointmentsCount() {
        return appointmentService.getScheduledAppointmentsCount();
    }

    @GetMapping("/organizations")
    public List<UserDTO> getAllOrganizations() {
        return appointmentService.getAllOrganizations();
    }
}

