package com.example.myproject;

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
public class GetById {
	
	Reclamation reclamationToRetrieve;

	@Mock
	ReclamationRepo reclamationRepo;
	
	@InjectMocks
	ReclamationService reclamationService;
	 
	@PostConstruct
	public void init()
	{
		 MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Given("^Reclamation with id \"([^\"]*)\" \"([^\"]*)\"$")
	public void Reclamation_with_id(Long id, String exist) throws Throwable {
		  this.initMocks(id, exist);
		  reclamationToRetrieve=new Reclamation.Builder().id(id).build();
	}

	@When("^User get this reclamation with \"([^\"]*)\"$")
	public void user_get_this_reclamation_with(Long id) throws Throwable {
		reclamationToRetrieve = reclamationService.getReclamationById(id);

	}

	@Then("^The reclamation retrieved should be \"([^\"]*)\"$")
	public void the_reclamation_retrieved_should_be(String isNull) throws Throwable {
		 if(isNull.equals("yes")) {
			   	Assert.assertNull(reclamationToRetrieve);
		   }else if(isNull.equals("no")) {
			   Assert.assertNotNull(reclamationToRetrieve);
		   }
	}
	
	
	private void initMocks(Long id, String exist)
	{
		
			if(exist.equals("yes")) {
				Mockito.when(reclamationService.getReclamationById(id)).thenReturn(new Reclamation.Builder().id(id).build());	
			}else if(exist.equals("no")) {
				Mockito.when(reclamationService.getReclamationById(id)).thenReturn(null);	
			}
		
	}

}
