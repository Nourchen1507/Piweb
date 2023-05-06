package com.example.myproject.Controller;

import java.time.LocalDateTime;
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

import com.example.myproject.Service.ReclamationService;
import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;
import com.example.myproject.repositories.UserRepository;


@RestController
@RequestMapping("/api/reclamation")
public class ReclamationController {
	
	@Autowired
    ReclamationService reclamationService;
	
	@Autowired  
	UserRepository userRepo;
	
	@PostMapping("/{from}/to/{to}")
    public ResponseEntity<Reclamation> createReclamation(@PathVariable("from") Long fromId, @PathVariable("to") Long toId, @RequestBody Reclamation reclamation) {
		
		Optional<User> from = userRepo.findById(fromId);
		Optional<User> to = userRepo.findById(toId);
		
			reclamation.setFrom(from.get());
			reclamation.setTo(to.get());
			reclamation.setCreatedOn(LocalDateTime.now());
			
		if(reclamation.getIsSignal()!=null && reclamation.getIsSignal()) {
			//signaling
			reclamationService.signal(reclamation);
	        return ResponseEntity.status(HttpStatus.CREATED).body(reclamation);

		}else {
			//feedbacking or rating or block
			Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
		}
		
		
		
		}
	
	
	@PostMapping("/user")
    public ResponseEntity<User> createuser(@RequestBody User user) {
		User created = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
	
	


	//its not the way of the traditional getAll() method, its based on pagination to improve the performance of our app
    @GetMapping("/reclamations/{exactly}")
    @CrossOrigin
    public ResponseEntity<Page<Reclamation>> getAllReclamations(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size, @PathVariable String exactly) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reclamation> pageReclamations = reclamationService.getAllReclamations(pageable, exactly);
        return new ResponseEntity<>(pageReclamations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        return new ResponseEntity<>(reclamation, HttpStatus.OK);
    }

    @PutMapping
    @CrossOrigin
    public ResponseEntity<Reclamation> updateReclamation(
            @RequestBody Reclamation reclamation) {
    	System.out.println("reclamation"+reclamation);
    	Reclamation updatedReclamation = reclamationService.updateReclamation(reclamation);
        return ResponseEntity.ok(updatedReclamation);
    }
    
    
    //here, no content returns 204 status, which means successful operation
    @DeleteMapping("/{reclamationId}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long reclamationId) {
        reclamationService.deleteReclamation(reclamationId);
        return ResponseEntity.noContent().build();

    }}
        