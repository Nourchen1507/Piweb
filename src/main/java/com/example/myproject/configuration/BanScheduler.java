package com.example.myproject.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.myproject.Repository.UserRepository;
import com.example.myproject.Service.ReclamationService;
import com.example.myproject.entities.User;


@Configuration
@EnableScheduling
public class BanScheduler {
	
	
	@Autowired
	private UserRepository userrepo;
	@Autowired
	private ReclamationService reclamationService;

	

	    @Scheduled(cron = "0 0 0 * * ?") // every midnight
	    public void sendEmail() {
	    	
	    	//preparing unban email body
	    	String body= 
    				"<html><head><style>" +
    	                    "h1 { color: #255c25; }" +
    	                    "h2 { color: #255c25; }" +
    	                    "</style></head><body>" +
    	                    "<h1>Hello, </h1>" 
    	                    + "<h2>We are happy to inform you that your account has been successfully reactivated. You can now access all the features of our platform and enjoy our service.\n</h2>"
    		    			+ "\n"
    		    			+ "<h2>We apologize for any inconvenience this may have caused. If you have any questions or concerns, please do not hesitate to contact us.\n</h2>"
    		    			+ "\n"
    		    			+ "<h2>Best regards.</h2>"+
    	                    "</body></html>";
	    			
	    			
	
	//get banned user which we should unban today
	List<User> filteredUser = userrepo.findAll()
			.stream()
		    .filter(jsonObject -> {
		        LocalDate today = LocalDate.now();
		        return jsonObject.getUnbanDate() != null && jsonObject.getUnbanDate().toLocalDate().equals(today);
		    })
		    .collect(Collectors.toList());
	
	
	filteredUser.forEach(e->reclamationService.sendEmail(e.getMailAddress(), "End of your account suspension", body));
	filteredUser.forEach(e->e.setUnbanDate(null));
	userrepo.saveAll(filteredUser);
	
	
	 

	    }
	
	
    
}