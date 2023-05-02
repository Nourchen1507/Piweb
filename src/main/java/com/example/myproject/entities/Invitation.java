package com.example.myproject.entities;

import java.io.Serializable;
import java.util.List;
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
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long idInvitation;
	private String name;
	private String helperInvited;

	@Enumerated(EnumType.STRING)
	Status statut;
	
	@ManyToOne
	@JsonIgnore
    private Event event;


}
