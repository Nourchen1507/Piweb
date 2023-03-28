package com.example.myproject;

import static org.junit.Assert.assertNotNull; 

import java.util.ArrayList;
import java.util.List;

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
public class GetAll {
	
	List<Reclamation> foundList;
		
		@Mock
		ReclamationRepo reclamationRepo;
		
		@InjectMocks
		ReclamationService reclamationService;
		 
		@PostConstruct
		public void init()
		{
			 MockitoAnnotations.initMocks(this);
		}
	
	
	@Given("^list of Reclamations in db is \"([^\"]*)\"$")
	public void list_of_Reclamations_in_db_is(String empty) throws Throwable {
		this.initMocks(empty);
		foundList=new ArrayList<>();

	}

	@When("^User retrieve all Reclamations from database$")
	public void user_retrieve_all_Reclamations_from_database() throws Throwable {
		foundList = reclamationService.getAllReclamations(null).toList();

	}

	@Then("^list retrieved should be \"([^\"]*)\"$")
	public void list_retrieved_should_be(String empty) throws Throwable {
	    
		if (empty.equals("empty")) {
			  Assert.assertTrue(foundList.isEmpty());}
				else if (empty.equals("notEmpty")) {
					assertNotNull(foundList);
				}
	}
	
	
	private void initMocks(String empty)
	{
		List<Reclamation> reclamationsList= new ArrayList<>();
		reclamationsList.add(new Reclamation.Builder().id(1L).build());
			if(empty.equals("empty")) {
				Mockito.when(reclamationService.getAllReclamations(null).toList()).thenReturn(null);	
			}else if(empty.equals("notEmpty")) {
				Mockito.when(reclamationService.getAllReclamations(null).toList()).thenReturn(reclamationsList);	
			}
		
	}

}
