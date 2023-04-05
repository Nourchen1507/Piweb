package com.example.myproject.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.print.attribute.standard.DateTimeAtCompleted;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private String mailAddress;
	private String phoneNumber;
	private String name;
	private String location;
	private String password;
	private Role role;
	private LocalDateTime unavailibility;
	private Boolean isDisabled;
	private String certificat;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Reclamation> reclamations = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();
	
	@OneToMany(mappedBy = "helper", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> helperAppointments = new ArrayList<>();
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> organizationAppointments = new ArrayList<>();
	
	
	
}
