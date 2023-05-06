package com.example.myproject.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
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

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "event")
public class Event implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)


	private Long idEvent;
	@NonNull
	private String name;
	@NonNull
	private LocalDate date;
	@NonNull
	private String lieu;
	@NonNull
	private String description;
	

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
    private User user;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<Invitation> invitations = new LinkedHashSet<>();





}
