package com.example.myproject.Repository;

import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;

@Repository
public interface ReclamationRepo extends PagingAndSortingRepository<Reclamation, Long> {
	
    Page<Reclamation> findAllByFeedbackIsNotNull(Pageable pageable);
    Page<Reclamation>  findByUser(User user, Pageable pageable); 


}