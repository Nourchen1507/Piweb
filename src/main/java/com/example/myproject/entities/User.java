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
@Table(	name = "user",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "userName"),
				@UniqueConstraint(columnNames = "mailAddress")
		})


public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUser;
	@Column(unique = true)
	private String userName;
	@Column(unique = true)
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


	public String getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(String imageProfile) {
		this.imageProfile = imageProfile;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getUserPhone() {
		return userPhone;
	}


	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Reclamation> reclamations = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "helper", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> helperAppointments = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Appointment> organizationAppointments = new ArrayList<>();




}