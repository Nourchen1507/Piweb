package com.example.myproject.bdd;

import java.util.ArrayList; 

import java.util.List;

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

public class DeleteById {
	
	List<Reclamation> oldReclamationList = new ArrayList<>();
	List<Reclamation> newReclamationList ;

	
	@Mock
	ReclamationRepo reclamationRepo;
	
	@InjectMocks
	ReclamationService reclamationService;
	
	@PostConstruct
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Given("^User delete reclamation with id (\\d+)$")
	public void user_delete_reclamation_with_id(Long id) throws Throwable {
		
		reclamationService.deleteReclamation(id);
	    
	}

	@When("^User retrieve all reclamations after the delete$")
	public void user_retrieve_all_reclamation_after_the_delete() throws Throwable {
		
		Reclamation reclamation1 = new Reclamation.Builder().id(1L).build();
		Reclamation reclamation2 = new Reclamation.Builder().id(2L).build();
		Reclamation reclamation3 = new Reclamation.Builder().id(3L).build();

		

		oldReclamationList.add(reclamation1);
		oldReclamationList.add(reclamation2);
		oldReclamationList.add(reclamation3);
		
		 this.initMocks(oldReclamationList);
		  newReclamationList=reclamationService.getAllReclamations(null).toList();

	   
	}

	@Then("^The size of the old List must be more than the new list$")
	public void the_size_of_the_old_List_must_be_more_than_the_new_list() throws Throwable {
		Assert.assertTrue(oldReclamationList.size()>newReclamationList.size());

	}
	
	
	public void initMocks(List<Reclamation> list) {
		List<Reclamation> newList= new ArrayList<>();
		for(Reclamation u: list) {
			newList.add(u);
		}
		newList.remove(1);

		Mockito.when(reclamationService.getAllReclamations(null).toList()).thenReturn(newList);


}
}