package com.example.myproject.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "appointment")
public class Appointment implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime date;
	private String lieu;
	
	@ManyToOne
	@JoinColumn(name = "helper_id")
    private User helper;

	public User getHelper() {
		return helper;
	}

	public void setHelper(User helper) {
		this.helper = helper;
	}

	@ManyToOne
	@JoinColumn(name = "organization_id")
    private User organization;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "alarm_id")
	private Alarm alarm;




}
