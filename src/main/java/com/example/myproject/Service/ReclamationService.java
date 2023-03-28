package com.example.myproject.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.entities.Reclamation;

@Service
public class ReclamationService {
	
	@Autowired
	ReclamationRepo reclamationRepo;

	 public Page<Reclamation> getAllReclamations(Pageable pageable) {
	        return reclamationRepo.findAll(pageable);
	    }

	 
	 //here we have created our own exception
	    public Reclamation getReclamationById(Long id) {
	        return reclamationRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Reclamation not found with id"+id));
	    }

	    public Reclamation createReclamation(Reclamation reclamation) {
	    	reclamation.setCreatedOn(LocalDateTime.now());

	        return reclamationRepo.save(reclamation);
	    }

	    public Reclamation updateReclamation(Reclamation reclamationDetails) {
	    	Reclamation reclamation = getReclamationById(reclamationDetails.getId());
	    	reclamation.setBanDate(reclamationDetails.getBanDate());
	    	reclamation.setBlockedBy(reclamationDetails.getBlockedBy());
	    	reclamation.setFeedBack(reclamationDetails.getFeedBack());
	    	reclamation.setIsBan(reclamationDetails.getIsBan());
	    	reclamation.setIsRate(reclamationDetails.getIsRate());
	    	reclamation.setIsSignal(reclamationDetails.getIsSignal());
	    	reclamation.setRaison(reclamationDetails.getRaison());	    	
	    	reclamation.setCreatedOn(LocalDateTime.now());
	        return reclamationRepo.save(reclamation);
	    }

	    public void deleteReclamation(Long id) {
	    	Reclamation reclamation = getReclamationById(id);
	    	reclamationRepo.delete(reclamation);
	    }
}
