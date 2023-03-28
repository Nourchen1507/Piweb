package com.example.myproject.Controller;

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


@RestController
@RequestMapping("/api/reclamation")
public class ReclamationController {
	
	@Autowired
    ReclamationService reclamationService;
	
	
	@PostMapping
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
		Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
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
        