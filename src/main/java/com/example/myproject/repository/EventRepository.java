package com.example.myproject.repository;


import com.example.myproject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>{

    List<Event> findByIdEvent(Long idEvent);
    List<Event>findByName(String name);
    List<Event> findByLieu(String Lieu);

Event findFirstByNameAndLieuAndDate(
        String name,
            String lieu,
            String date);



}
