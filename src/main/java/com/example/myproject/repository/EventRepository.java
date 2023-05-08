package com.example.myproject.repository;


import com.example.myproject.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer>{

    List<Event> findByIdEvent(Integer idEvent);
    List<Event>findByName(String name);
    List<Event> findByLieu(String Lieu);

    @Query("SELECT e FROM Event e WHERE e.name =:name")
    Event retrieveEventByName(@Param("name") String name);

    @Query("SELECT count(i) FROM Invitation i WHERE i.event.idEvent =:idE")
    Integer countI(@Param("idE") int idE);


Event findFirstByNameAndLieuAndDate(
        String name,
        String lieu,
        String date);
}
