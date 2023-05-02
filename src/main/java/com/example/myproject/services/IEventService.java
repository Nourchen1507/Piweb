package com.example.myproject.services;


import com.example.myproject.entities.Event;
import lombok.extern.java.Log;

import java.util.List;

public interface IEventService {

    void addEvent(Event event);

   Event updateEvent(Long idEvent, Event event);

    void removeEvent(Long idEvent);

    List<Event> retrieveEvent(Long idEvent);

   List<Event> getAllEvent();

    List<Event> getEventByName(String name);


    List<Event> getEventByLieu(String lieu);




    // UniversityDto addUniversityDto(UniversityDto universityDto);

   // List<UniversityDto> retrieveAllUniversityDto();







}
