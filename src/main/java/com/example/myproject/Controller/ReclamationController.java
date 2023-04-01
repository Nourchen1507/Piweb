package com.example.myproject.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.*;

import com.example.myproject.Repository.UserRepository;
import com.example.myproject.Service.ReclamationService;
import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;


@RestController
@RequestMapping("/api/reclamation")
public class ReclamationController {
	
	@Autowired
    ReclamationService reclamationService;
	
	@Autowired  
	UserRepository userRepo;
	
	@PostMapping("/reclamation/{userId}")
    public ResponseEntity<Reclamation> createReclamation(@PathVariable Long userId, @RequestBody Reclamation reclamation) {
		Optional<User> user = userRepo.findById(userId);
		
		if( user.isPresent()) {
			
			reclamation.setUser(user.get());
			System.out.println("reclamation=================="+reclamation);
			
		if(reclamation.getIsSignal()!=null) {
			
			reclamationService.signal(userId,reclamation);
	        return ResponseEntity.status(HttpStatus.CREATED).body(reclamation);

		}else {
			
			Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
		}
		}else {
			
	        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(reclamation);

		}
		
		
		}
	
	
	@PostMapping("/user")
    public ResponseEntity<User> createuser(@RequestBody User user) {
		User created = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
	
	


	//its not the traditional getAll() method, its based on pagination to improve the performance of our app
    @GetMapping
    public ResponseEntity<Page<Reclamation>> getAllReclamations(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reclamation> pageReclamations = reclamationService.getAllReclamations(pageable);
        return new ResponseEntity<>(pageReclamations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        return new ResponseEntity<>(reclamation, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Reclamation> updateProduct(
            @RequestBody Reclamation reclamation) {
    	Reclamation updatedReclamation = reclamationService.updateReclamation(reclamation);
        return ResponseEntity.ok(updatedReclamation);
    }
    
    
    //here, no content returns 204 status, which means successful operation
    @DeleteMapping("/{reclamationId}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long reclamationId) {
        reclamationService.deleteReclamation(reclamationId);
        return ResponseEntity.noContent().build();

    }}
        