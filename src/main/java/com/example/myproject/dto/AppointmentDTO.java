package com.example.myproject.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonPropertyOrder({ "id"})
public class AppointmentDTO extends CreateUpdateAppointmentDTO {
	
	private Long id;

}
