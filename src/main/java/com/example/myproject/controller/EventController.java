package com.example.myproject.controller;


import com.example.myproject.repository.EventRepository;
import com.example.myproject.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.example.myproject.entities.Event;

import java.util.List;


@RestController
@Component
@RequestMapping("/event")
public class EventController {

    @Autowired
    private IEventService iEventService;

    @PostMapping("/add")
    void addEvent(@RequestBody Event event){
        iEventService.addEvent(event);
    }

    @DeleteMapping("/delete/{id}")
    void deleteEvent(@PathVariable("id") Long idEvent){
       iEventService.removeEvent(idEvent);
    }


    @PutMapping("/update/{id}")
   Event updateEvent(@PathVariable("id") Long idEvent , @RequestBody Event event){
        return iEventService.updateEvent(idEvent,event);
    }

    @GetMapping("/get/{id}")
    List<Event> getEvent(@PathVariable("id") Long idEvent){
        return iEventService.retrieveEvent(idEvent);
    }

    @GetMapping ("/all")
    List<Event> getAllEvent(){
        return iEventService.getAllEvent();
    }



}