package com.example.myproject.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;

@Service
public class ReclamationService {
	
	@Autowired
	ReclamationRepo reclamationRepo;
	
	@Autowired
    private JavaMailSender javaMailSender;

	 public Page<Reclamation> getAllReclamations(Pageable pageable) {
	        return reclamationRepo.findAllByFeedbackIsNotNull(pageable);
	    }

	 
	 //here we have created our own exception
	    public Reclamation getReclamationById(Long id) {
	        return reclamationRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Reclamation not found with id"+id));
	    }

	    
	    //to change, i should insert also the user
	    public Reclamation createReclamation( Reclamation reclamation) {
	    	reclamation.setCreatedOn(LocalDateTime.now());
	    	
	        return reclamationRepo.save(reclamation);
	    }

	    public Reclamation updateReclamation(Reclamation reclamationDetails) {
	    	Reclamation reclamation = getReclamationById(reclamationDetails.getId());
	    	reclamation.setFeedback(reclamationDetails.getFeedback());
	    	reclamation.setRateLevel(reclamationDetails.getRateLevel());
	        return reclamationRepo.save(reclamation);
	    }

	    public void deleteReclamation(Long id) {
	    	Reclamation reclamation = getReclamationById(id);
	    	reclamationRepo.delete(reclamation);
	    }
	    
	    
	    public void signal(Long userId) {
	    	
	    	User user = new User();
	    	//user = userepo.findById(userId);
	    	List<Reclamation> signals = reclamationRepo.findByUser(user, null)
	    			.filter(reclamation->reclamation.getIsSignal()).toList();
	    	
//if he is signaled 5 times, he will be banned
	    	if(signals.size()==5) {
	    		if(user.getAlreadyBanned()) {
	    			
	    			//he will be banned temporary
		    		reclamationRepo.deleteAll(signals);
		    		//userrepo.save(user);
		    				    		
		    		
		    		//prepare subject with html and styled with css
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #007bff; }" +
		    	                    "h2 { color: #28a745; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+user.getName()+"</h1>" +
		    	                    "<h2>We regret to inform you that your account has been banned definitively from our</h2>" +
		    	                    "<h2>platform. We have taken this action because you have already been banned and this is the second one. </h2>" +
		    	                    "</body></html>";
		    				
		    		sendEmail(user.getMailAddress(), "Your account has been banned!", subject);
		    		//userrepo.deletebyid(user.id);
	    		}else {
	    			
	    			LocalDateTime localDateTime = LocalDateTime.now();

	    			// convert LocalDateTime to ZonedDateTime with default time zone
	    			ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

	    			// get Date from ZonedDateTime
	    			Date unbanDate = Date.from(zonedDateTime.toInstant());

	    			
	    			
		    		user.setAlreadyBanned(true);
		    		reclamationRepo.save(new Reclamation.Builder().isBan(true).unbanDate(LocalDateTime.now()).build());

		    		
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #007bff; }" +
		    	                    "h2 { color: #28a745; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+user.getName()+"</h1>" +
		    	                    "<h2>We regret to inform you that your account has been banned temporary from our</h2>" +
		    	                    "<h2>platform. We have taken this action because you have been signaled </h2>" +
		    	                    "<h2>5 times, due to theses reasons:  </h2>" +
		    	                    "<p>"+ 
		    	                    signals.stream()
				                    .map(Reclamation::getRaison)
				                    .collect(Collectors.joining(System.lineSeparator()))+"."+
		    	                    "<h2>You will be unbanned the "+unbanDate.toString()+"  </h2>" 
		    	                    +"</p>" +
		    	                    "</body></html>";
		    				
		    		sendEmail(user.getMailAddress(), "Your account has been banned!", subject);
		    		
	    		}
	    		
	    		
	    	}else {
	    		reclamationRepo.save(new Reclamation.Builder().isSignal(true).build());
	    	}
	    	
	    	
	    }
	    
	    
	    public void sendEmail(String to, String subject, String body) {

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);

	        javaMailSender.send(message);
	    }
	    
	    
	    
	    

	    
}
