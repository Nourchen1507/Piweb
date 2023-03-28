package com.example.myproject.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entities.Reclamation;

@Repository
public interface ReclamationRepo extends PagingAndSortingRepository<Reclamation, Long> {

}