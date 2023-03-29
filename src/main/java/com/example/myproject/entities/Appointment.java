package com.example.myproject.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@JsonIgnore
    private User helper;
	
	@ManyToOne
	@JoinColumn(name = "organization_id")
	@JsonIgnore
    private User organization;
	
	
	@OneToMany(mappedBy = "appointment", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Alarm> alarms = new ArrayList<>();
	
	

}
