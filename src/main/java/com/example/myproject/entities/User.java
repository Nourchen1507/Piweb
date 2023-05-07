package com.example.myproject.entities;

import java.io.Serializable; 

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

import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCompleted;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 


@Getter
@Setter
@Data
@Entity
//@Table(name = "user",
//uniqueConstraints = {
//    @UniqueConstraint(name = "uk_username", columnNames = "userName"),
//    @UniqueConstraint(name = "uk_mail", columnNames = "mailAddress")
//}
//)

public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//@Column(unique = true,  length = 255)
	private String userName;
	//@Column(unique = true, length = 255)
	private String mailAddress;
	private String userPhone;
	@Column(name = "imageProfile",columnDefinition = "longtext")
	private String imageProfile;
	private String location;
	private String password;
	private LocalDateTime unavailibility;
	private Boolean isDisabled;
	private String certificat;
	private Boolean alreadyBanned;
	private LocalDateTime unbanDate;
	
	private String certificate;
	private String verificationToken;
	private boolean verified;
	private String userCode;
	private long rateAverage;


//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "USER_ROLE",
//			joinColumns = {
//					@JoinColumn(name = "USER_ID")
//			},
//			inverseJoinColumns = {
//					@JoinColumn(name = "ROLE_ID")
//			}
//	)
//	private Set<Role> role;


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

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Don> dons = new ArrayList<>();
	


	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Like> likes = new ArrayList<>();
}


