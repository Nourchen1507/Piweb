package com.example.myproject;


import javax.annotation.PostConstruct; 

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.myproject.Repository.ReclamationRepo;
import com.example.myproject.Service.ReclamationService;
import com.example.myproject.entities.Reclamation;

import cucumber.api.java.en.*;

public class UpdateById {
	
Reclamation oldReclamation ,newReclamation1;
	
	
	@Mock
	ReclamationRepo reclamationRepo;
	
	@InjectMocks
	ReclamationService reclamationService;
	
	@PostConstruct
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Given("^User retrieve reclamation with id \"([^\"]*)\" ,old feedback \"([^\"]*)\"")
	public void user_retrieve_reclamation_with_id_old_feedback_and_new_feedback(Long id, String oldfeedback, String newfeedback) throws Throwable {
		 this.initMocks(id,oldfeedback,null);
		   oldReclamation = reclamationService.getReclamationById(id);
	}

	@When("^User update reclamation with id \"([^\"]*)\" at his feedback \"([^\"]*)\" with the new feedback \"([^\"]*)\"$")
	public void user_update_Reclamation_with_id_at_his_feedback_with_the_new_feedback(Long id, String oldfeedback, String newfeedback) throws Throwable {
		 this.initMocks(id, oldfeedback, newfeedback);
		   newReclamation1 = reclamationService.updateReclamation(newReclamation1);
	}

	@Then("^Assert that Reclamation has new feedback \"([^\"]*)\"$")
	public void assert_that_Reclamation_has_new_feedback(String newn) throws Throwable {
		   Assert.assertTrue(newReclamation1.getFeedback().equals(newn));
	}
	
	public void initMocks(Long id, String oldfeedback, String newfeedback) {
		Reclamation retrievedReclamation = new Reclamation.Builder().id(1L).feedback(oldfeedback).build();
				Mockito.when(reclamationService.getReclamationById(id)).thenReturn(retrievedReclamation);
		Mockito.when(reclamationService.updateReclamation(newReclamation1)).thenReturn(new Reclamation.Builder().id(1L).feedback(newfeedback).build());

	}

}
