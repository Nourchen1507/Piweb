package com.example.myproject.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter

public class Role {

	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;	
    private String roleName;
    


}
