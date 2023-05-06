package com.example.myproject.entities;

import java.time.LocalDateTime; 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter 
@Table(name = "reclamation")
@NoArgsConstructor
@ToString
public class Reclamation {
	

	
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Boolean isSignal; 
	
	private Boolean isBan;
	
	private Integer rateLevel;
	
	private LocalDateTime unbanDate;
	
	private String feedback;
	
	private String raison;
	
	private Boolean isBlock;
	
	private LocalDateTime createdOn;
	
	@ManyToOne
	@JoinColumn(name = "from_id")
    private User from;
	
	
	@ManyToOne
	@JoinColumn(name = "to_id")
    private User to;
	
	
	
	
	
	public void setRateLevel(Integer rateLevel) {
		this.rateLevel = rateLevel;
	}


	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}



	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	public void setFrom(User user) {
		this.from = user;
	}
	
	public void setTo(User user) {
		this.to = user;
	}
	
	public void setRaison(String raison) {
		this.raison = raison;
	}


	public Reclamation(Builder builder) {
		super();
		this.id = builder.id;
		this.isSignal = builder.isSignal;
		this.isBan = builder.isBan;
		this.rateLevel = builder.rateLevel;
		this.unbanDate = builder.unbanDate;
		this.feedback = builder.feedback;
		this.raison = builder.raison;
		this.isBlock = builder.isBlock;
		this.createdOn = builder.createdOn;
		this.from = builder.from;
		this.to = builder.to;

	}


	// Builder pour créer de nouveaux objets Person
    public static class Builder {
    	private Long id;
    	private Boolean isSignal;
    	private Boolean isBan;
       	private Integer rateLevel;
       	private LocalDateTime unbanDate;
    	private String feedback;
       	private String raison;
    	private Boolean isBlock;
    	private LocalDateTime createdOn;
    	private User from;
    	private User to;

    	
        public Builder id(Long id) {
            this.id=id;
            return this;
        }

        public Builder isSignal(Boolean isSignal) {
            this.isSignal=isSignal;
            return this;
        }

        public Builder isBan(Boolean isBan) {
            this.isBan=isBan;
            return this;
        }

        public Builder rateLevel(Integer rateLevel) {
            this.rateLevel=rateLevel;
            return this;
        }
        
        public Builder unbanDate(LocalDateTime banDate) {
            this.unbanDate=banDate;
            return this;
        }

        
        public Builder feedback(String feedback) {
            this.feedback=feedback;
            return this; 
        }
        
        public Builder raison(String raison) {
            this.raison=raison;
            return this;
        }

        public Builder isBlock(Boolean blocker) {
            this.isBlock=blocker;
            return this;
        }
        
        public Builder createdOn(LocalDateTime createdOn) {
            this.createdOn=createdOn;
            return this;
        }
        
        public Builder to(User user) {
            this.to=user;
            return this;
        }
        
        public Builder from(User user) {
            this.from=user;
            return this;
        }

        public Reclamation build() {
            return new Reclamation(this);
        }
    }
	
	
	
	
	

}
