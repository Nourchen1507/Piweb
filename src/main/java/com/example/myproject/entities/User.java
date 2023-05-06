package com.example.myproject.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCompleted;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user") 
@NoArgsConstructor
public class User {

	@Id
	private Long id;
	private String userName;
	private String mailAddress;
	private String userPhone;
	private String location;
	private String password;
	private LocalDateTime unavailibility;
	private Boolean isDisabled;
	private String certificat;
	private Boolean alreadyBanned;
	private LocalDateTime unbanDate;
	
	private String verificationToken;
	private int isverified;
	private String userCode;
	private long rateAverage;


	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE",
			joinColumns = {
					@JoinColumn(name = "USER_ID")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID")
			}
	)
	private Set<Role> role;


	

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	
	@OneToMany(mappedBy = "from", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Reclamation> froms = new ArrayList<>();
	
	@OneToMany(mappedBy = "to", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Reclamation> tos = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();
	
	@OneToMany(mappedBy = "helper", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> helperAppointments = new ArrayList<>();
	
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> organizationAppointments = new ArrayList<>();




}
