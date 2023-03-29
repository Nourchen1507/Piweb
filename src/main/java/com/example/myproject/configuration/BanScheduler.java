package com.example.myproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.Service.ReclamationService;

@Configuration
@EnableScheduling
public class BanScheduler {
	
	
	@Autowired
	private ReclamationService reclamationService;

	public void checkiftodayToUnbanHim() {
//	    Entity entity = entityRepository.findById(1L).orElse(null);
//	    if (entity != null && isDateEqualToday(entity.getDate())) {
//	        String to = "recipient@example.com";
//	        String subject = "Entity Date Equals Today";
//	        String body = "The date value of the entity stored in the database is equal to today's date.";
//	        sendEmail(to, subject, body);
//	    }
	}
	

    @Scheduled(cron = "0 0 0 * * *") // runs every day at midnight
    public void runScheduledTask() {
       // checkEntityDateAndSendEmail();
    }
}