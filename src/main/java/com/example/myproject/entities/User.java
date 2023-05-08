package com.example.myproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter 
@Setter
@Data
@Entity
//@Table(	name = "user",
//		uniqueConstraints = {
//				@UniqueConstraint(columnNames = "userName"),
//				@UniqueConstraint(columnNames = "mailAddress")
//		})


public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//@Column(unique = true)
	private String userName;
	//@Column(unique = true)
	private String mailAddress;
	private String userPhone;
	@Column(name = "imageProfile",columnDefinition = "longtext")
	private String imageProfile;
	private String location;
	private String password;
	@Column(name = "certificate",columnDefinition = "longtext")
	private String certificate;
	private String verificationToken;
	private boolean verified;
	private String userCode;
	@OneToOne
	@JoinColumn(name = "role_role_name")
	private Role role;


	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "from", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Reclamation> froms = new ArrayList<>();
	
	@OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Reclamation> tos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "helper", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> helperAppointments = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> organizationAppointments = new ArrayList<>();

	private long rateAverage;
	
	private Boolean alreadyBanned;

	private LocalDateTime unbanDate;



}