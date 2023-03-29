package com.example.myproject.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "invitation")

public class Invitation implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@NonNull
	private Long idInvitation;
	@NonNull
	private String name;
	@NonNull
	private Long helperInvited;


	@Enumerated(EnumType.STRING)
	Status statut;
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	@JsonIgnore
    private Event event;



}