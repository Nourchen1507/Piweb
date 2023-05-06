package com.example.myproject.controller;
import com.example.myproject.dto.AppointmentDTO;
import com.example.myproject.dto.CreateUpdateAppointmentDTO;
import com.example.myproject.entities.Appointment;
import com.example.myproject.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;


    @PostMapping
    @CrossOrigin
    public AppointmentDTO createAppointment(@RequestBody CreateUpdateAppointmentDTO appointment) {

        return appointmentService.createAppointment(appointment);
    }




}