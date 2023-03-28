package com.example.myproject.entities;

import java.time.LocalDateTime; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reclamation")
public class Reclamation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nullable
	private Boolean isSignal;
	
	@Nullable
	private Boolean isBan;
	
	@Nullable
	private Boolean isRate;
	
	@Nullable
	private LocalDateTime banDate;
	
	@Nullable
	private String FeedBack;
	
	@Nullable
	private String raison;
	
	@Nullable
	private Long blockedBy;
	
	private LocalDateTime createdOn;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
    private User user;
	
	
	private Reclamation() {
		
	}
	
	
	
	// Builder pour créer de nouveaux objets Person
    public static class Builder {
        private final Reclamation reclamation = new Reclamation();
        
        public Builder id(Long id) {
            reclamation.setId(id);
            return this;
        }

        public Builder isSignal(Boolean isSignal) {
            reclamation.setIsSignal(isSignal);
            return this;
        }

        public Builder isBan(Boolean isBan) {
            reclamation.setIsBan(isBan);
            return this;
        }

        public Builder isRate(Boolean isRate) {
            reclamation.setIsRate(isRate);
            return this;
        }
        
        public Builder banDate(LocalDateTime banDate) {
            reclamation.setBanDate(banDate);
            return this;
        }

        
        public Builder feedback(String feedback) {
            reclamation.setFeedBack(feedback);
            return this;
        }
        
        public Builder raison(String raison) {
            reclamation.setRaison(raison);
            return this;
        }

        public Builder blockedBy(Long blocker) {
            reclamation.setBlockedBy(blocker);
            return this;
        }
        
        public Builder createdOn(LocalDateTime createdOn) {
            reclamation.setCreatedOn(createdOn);
            return this;
        }

        public Reclamation build() {
            return reclamation;
        }
    }
	
	
	
	
	

}
