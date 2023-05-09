package com.example.myproject.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
	private Long id;
	private LocalDateTime date;
	private String lieu;
	private UserDTO helper;
	private UserDTO organization;
}
