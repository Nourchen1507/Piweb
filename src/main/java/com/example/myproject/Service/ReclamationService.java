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
import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;
import com.example.myproject.repositories.UserRepository;

@Service
public class ReclamationService {
	
	@Autowired
	ReclamationRepo reclamationRepo;
	@Autowired 
	UserRepository userRepo;
	
	
	@Autowired
    private JavaMailSender javaMailSender;

	 public Page<Reclamation> getAllReclamations(Pageable pageable, String exactly) {
		 if (exactly.equals("feedBack"))
	        return reclamationRepo.findAllByFeedbackIsNotNull(pageable);
		 else if (exactly.equals("rate"))
		        return reclamationRepo.findAllByRateLevelIsNotNull(pageable);
		 else 
		        return reclamationRepo.findAllByIsSignalIsNotNull(pageable);
	    }

	 
	 //here we have created our own exception
	    public Reclamation getReclamationById(Long id) {
	        return reclamationRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Reclamation not found with id"+id));
	    }

	    
	    //to change, i should insert also the user
	    public Reclamation createReclamation( Reclamation reclamation) {
	    	
	    	if(reclamation.getRateLevel()==-1) {
	    		reclamation.setRateLevel(null);
	    	}
	    	System.out.println("rate leveeeeeeeel= "+reclamation.getRateLevel());
	    	
	    	if(reclamation.getRateLevel()!=null  ) {
	    		
	    		long count = reclamationRepo.findAll().stream()
	    				.filter(rec->rec.getTo()!=null&& rec.getTo().getId()==reclamation.getTo().getId()
	    				&& rec.getRateLevel()!=null)
		    			.map(Reclamation::getRateLevel).count()+1;
		    	System.out.println("cout ================ "+count);

		    	long sum = reclamationRepo.findAll().stream()
		    			.filter(rec->rec.getTo()!=null&&rec.getTo().getId()==reclamation.getTo().getId()
		    			&& rec.getRateLevel()!=null)
		    			.mapToInt(Reclamation::getRateLevel).sum()+reclamation.getRateLevel();
		    	
		    	System.out.println("cout ================ "+count);
		    	System.out.println("sum ================ "+sum);

		    	reclamation.getTo().setRateAverage((long) sum/count);
		    	System.out.println("average = " +reclamation.getTo().getRateAverage());
		    	userRepo.save(reclamation.getTo());
		    	
	    	}
	    	
	    	System.out.println("rate leveeeeeeeel= "+reclamation.getRateLevel());

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
	    
	    
	    public void signal(Reclamation reclamation) {
	    	
	    		reclamation.setRateLevel(null);
	    	
	    	
	    	//User user = new User();
	    //	User user = userRepo.findById(id).get();
	    	List<Reclamation> signals = reclamationRepo.findByTo(reclamation.getTo(), null).toList();
	    	for (Reclamation reclamationn : signals) {
	    	    System.out.println("isSignal=" + reclamationn.getIsSignal());
	    	}
	    	List<Reclamation> signalss = signals.stream()
	    	    .filter(reclamationn -> reclamationn.getIsSignal())
	    	    .toList();
	    	System.out.println("filtered"+signalss);
//if he is signaled 5 times, he will be banned
	    	if(signalss.size()>=2) {
	    		
	    		if( reclamation.getTo().getAlreadyBanned()) {
		    		System.out.println("already banned  !!!");

	    			
		    	//	userepo.save(user);
		    				    		
		    	//	reclamationRepo.deleteAll(signalss);
		    		System.out.println("signals to delete==========="+signalss);
		    		for (Reclamation recl : signalss) {
						reclamationRepo.deleteById(recl.getId());
					}

		    		//prepare subject with html and styled with css
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #8B0000; }" +
		    	                    "h2 { color: #8B0000; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+reclamation.getTo().getUserName()+"</h1>" +
		    	                    "<h2>We regret to inform you that your account has been definitively banned from our</h2>" +
		    	                    "<h2>platform. We have taken this action because you have already been banned and this is the second one. </h2>" +
		    	                    "</body></html>";
		    				System.out.println("maail=========="+reclamation.getTo().getMailAddress());
		    				
				    		sendEmail(reclamation.getTo().getMailAddress(), "Your account has been banned!", subject);
		    		userRepo.deleteById(reclamation.getTo().getId());
	    		}else {
		    		System.out.println("not already banned  !!!");

	    			//he will be banned temporary
		    		
	    			LocalDateTime unbanDate = LocalDateTime.now();

	    			
	    			
	    			
	    			reclamation.getTo().setAlreadyBanned(true);
	    			reclamation.getTo().setUnbanDate(unbanDate);
		    		userRepo.save(reclamation.getTo());
		    		reclamationRepo.save(reclamation);

		    		
		    		String subject =
		    				"<html><head><style>" +
		    	                    "h1 { color: #8B0000; }" +
		    	                    "h2 { color: #8B0000; }" +
		    	                    "</style></head><body>" +
		    	                    "<h1>Dear "+reclamation.getTo().getUserName()+"</h1>" +
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
		    			
		    		System.out.println("usermail========="+reclamation.getTo().getMailAddress());
		    		sendEmail(reclamation.getTo().getMailAddress(), "Your account has been banned!", subject);
		    		
	    		}
	    		
	    		
	    	}else {
	    		reclamation.getTo().setAlreadyBanned(false);
	    		userRepo.save(reclamation.getTo());
	    		reclamationRepo.save(reclamation);
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

	  	     System.out.println("sendiiiiiiiiiiiiiiiiiiiiiiiiiing");
	  	        javaMailSender.send(message);
	    		
			} catch (Exception e) {
				System.out.println("erero maiiiiiiiiiil"+e.getMessage());
			}
	      
	    }
	    
	    
	   

	    
}
