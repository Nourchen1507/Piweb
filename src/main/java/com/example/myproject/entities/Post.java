package com.example.myproject.entities;

import java.time.LocalDate; 
import java.util.*;
import javax.persistence.*;
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
@NoArgsConstructor
@ToString
@Table(name = "post")



public class Post {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)


	private Long id;
	private String nom ;

	private LocalDate datecreation = LocalDate.now() ;
	private String description;
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@OneToMany(mappedBy = "post")
	private List<Don> dons = new ArrayList<>();


	@OneToMany(mappedBy = "post")
	private List<Like> likes = new ArrayList<>();


}
