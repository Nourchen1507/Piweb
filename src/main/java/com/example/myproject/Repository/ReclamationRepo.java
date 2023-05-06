package com.example.myproject.Repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entities.Reclamation;
import com.example.myproject.entities.User;

@Repository
public interface ReclamationRepo extends JpaRepository<Reclamation, Long> {
	
    Page<Reclamation> findAllByIsSignalIsNotNull(Pageable pageable);
    Page<Reclamation> findAllByRateLevelIsNotNull(Pageable pageable);
    Page<Reclamation> findAllByFeedbackIsNotNull(Pageable pageable);

    Page<Reclamation>  findByFrom(User user, Pageable pageable); 
    Page<Reclamation>  findByTo(User user, Pageable pageable); 


}