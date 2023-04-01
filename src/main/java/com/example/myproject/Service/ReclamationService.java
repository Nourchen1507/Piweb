package com.example.myproject.Service;

import java.time.LocalDateTime; 
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.Repository.UserRepository;
import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;

@Service
public class ReclamationService {
	
	@Autowired
	ReclamationRepo reclamationRepo;
	@Autowired 
	UserRepository userRepo;
	
	
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
	    
	    
	    public void signal(Long userId, Reclamation reclamation) {
	    	
	    	//User user = new User();
	    	User user = userRepo.findById(userId).get();
	    	List<Reclamation> signals = reclamationRepo.findByUser(user, null).toList();
	    	System.out.println("===========================all from user"+signals);
	    	List<Reclamation> signalss=signals.stream().filter(reclamationn->reclamationn.getIsSignal()).toList();
	    	System.out.println("===========================filtered"+signals);
	    	
//if he is signaled 5 times, he will be banned
	    	if(signalss.size()>=2) {
	    		if(user.getAlreadyBanned()) {
	    			
	    			
		    	//	userepo.save(user);
		    				    		
		    		reclamationRepo.deleteAll(signals);

		    		//prepare subject with html and styled with css
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #8B0000; }" +
		    	                    "h2 { color: #8B0000; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+user.getName()+"</h1>" +
		    	                    "<h2>We regret to inform you that your account has been definitively banned from our</h2>" +
		    	                    "<h2>platform. We have taken this action because you have already been banned and this is the second one. </h2>" +
		    	                    "</body></html>";
		    				
		    	//	sendEmail(user.getMailAddress(), "Your account has been banned!", subject);
		    		userRepo.deleteById(userId);
	    		}else {
	    			
	    			//he will be banned temporary
		    		reclamationRepo.deleteAll(signals);
		    		
	    			LocalDateTime unbanDate = LocalDateTime.now();

	    			
	    			
	    			
		    		user.setAlreadyBanned(true);
		    		user.setUnbanDate(unbanDate);
		    		userRepo.save(user);
		    		reclamationRepo.save(new Reclamation.Builder().isBan(true).build());

		    		
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #8B0000; }" +
		    	                    "h2 { color: #8B0000; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+user.getName()+"</h1>" +
		    	                    "<h2>We regret to inform you that your account has been banned temporary from our</h2>" +
		    	                    "<h2>platform. We have taken this action because you have been signaled </h2>" +
		    	                    "<h2>5 times, due to theses reasons:  </h2>" +
		    	                    "<p>"+ 
		    	                    signals.stream()
				                    .map(Reclamation::getRaison)
				                    .collect(Collectors.joining(" - \r\n"))+"."+
		    	                    "<h2>You will be unbanned the "+unbanDate.getYear()+" - "+unbanDate.getMonth()+" - "+unbanDate.getDayOfMonth()+"  </h2>" 
		    	                    +"</p>" +
		    	                    "</body></html>";
		    				
		    	//	sendEmail(user.getMailAddress(), "Your account has been banned!", subject);
		    		
	    		}
	    		
	    		
	    	}else {
	    		reclamationRepo.save(new Reclamation.Builder().isSignal(true).user(user).raison(reclamation.getRaison()).build());
	    	}
	    	
	    	
	    }
	    
	    
	    public void sendEmail(String to, String subject, String body) {

	      //  SimpleMailMessage message = new SimpleMailMessage();
	        
	    	try {
				
	    		  MimeMessage message = javaMailSender.createMimeMessage();
	  	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	  	        helper.setFrom("skander.kefi@esprit.tn");
	  	        helper.setTo(to);
	  	        helper.setSubject(subject);
	  	        helper.setText(body, true);

	  	     
	  	        javaMailSender.send(message);
	    		
			} catch (Exception e) {
				// TODO: handle exception
			}
	      
	    }
	    
	    
	   

	    
}
