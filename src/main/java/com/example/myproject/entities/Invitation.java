package com.example.myproject.entities;

import java.io.Serializable;
import java.util.Date;
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
	@Column(name="idInvitation")
	private int idInvitation;

	@Temporal (TemporalType.DATE)
	private Date dateInvitation;

	private String helperInvited;
	private Boolean archive;
	@Enumerated(EnumType.STRING)
	Status statut;
	
	@ManyToOne
	@JsonIgnore
    private Event event;


}
