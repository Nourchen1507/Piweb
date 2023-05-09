package com.example.myproject.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reclamation")
public class Reclamation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	private Boolean isSignal;
	private Boolean isBan;
	private Boolean isRate;
	private LocalDateTime banDate;
	private String FeedBack;
	private String raison;
	private Long blockedBy;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
    private User user;
	
	
	
	

}
