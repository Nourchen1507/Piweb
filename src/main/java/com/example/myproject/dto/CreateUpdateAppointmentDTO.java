package com.example.myproject.dto;

import com.example.myproject.entities.Alarm; 

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateUpdateAppointmentDTO {
	private LocalDateTime date;
	private String lieu;
	private Long helperId;
    private Long organizationId;
	private Boolean alarmActivated;
}
