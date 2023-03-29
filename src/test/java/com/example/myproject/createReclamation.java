package com.example.myproject;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct; 

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.Service.ReclamationService;
import com.example.myproject.entities.Reclamation;

import cucumber.api.java.en.*;

@SpringBootTest(classes=MyprojectApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class createReclamation {
	
	Reclamation reclamation;
	Reclamation found;

		
		@Mock
		ReclamationRepo reclamationRepo;
		
		@InjectMocks
		ReclamationService reclamationService;
		 
		@PostConstruct
		public void init()
		{
			 MockitoAnnotations.initMocks(this);

		}
		

	     
	@Given("^reclamation with (\\d+) \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"  \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"  \"([^\"]*)\" ")
	public void reclamation_with(int id, String isSignal, String isBan, String isRate, String banDate, String feedBack, String raison, String blockedBy, String exist) throws Throwable {
		 Long reclamationId = Long.valueOf(id);
		 
		 //reclamation creation using builder 
	  reclamation = new Reclamation.Builder().id(reclamationId).unbanDate(LocalDateTime.parse(banDate))
			  .feedback(feedBack).isBan(Boolean.valueOf(isBan))
			  .rateLevel(Integer.valueOf(isRate)).isSignal(Boolean.valueOf(isSignal)).build();
	  
	  this.initMocks(exist);
		found=reclamationRepo.findById(reclamationId).get();
	}

	@When("^user add this reclamation to list$")
	public void user_add_this_reclamation_to_list() throws Throwable {
		reclamationService.createReclamation(reclamation); 
	}

	@Then("^The result should be \"([^\"]*)\"$")
	public void the_result_should_be(String result) throws Throwable {
		
		if (result.equals("yes")) {
			Assert.assertNull(found);
		}else if (result.equals("no")){
			Assert.assertNotNull(found);

		}
	   
	}
	
	
	private void initMocks(String exist)
	{
			if(exist.equals("no")) {
			Mockito.when(reclamationService.getReclamationById(Mockito.anyLong())).thenReturn(null);	
			}else if(exist.equals("yes")) {
				Mockito.when(reclamationService.getReclamationById(Mockito.anyLong())).thenReturn( new Reclamation.Builder().build());	
			}
		
	}


}
