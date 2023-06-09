package com.example.myproject.controller;


import com.example.myproject.entities.Invitation;
import com.example.myproject.repository.EventRepository;
import com.example.myproject.repository.IInvitationRepository;
import com.example.myproject.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.example.myproject.entities.Event;

import java.util.List;




@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@Component
@RequestMapping("/event")
public class EventController {

    @Autowired
    private IEventService iEventService;

    private IInvitationRepository invitationRepository;

    @PostMapping("/add")
    void addEvent(@RequestBody Event event){
        iEventService.addEvent(event);
    }

    @DeleteMapping("/delete/{id}")
    void deleteEvent(@PathVariable("id") int idEvent){
       iEventService.removeEvent(idEvent);
    }


    @PutMapping("/update/{id}")
   Event updateEvent(@PathVariable("id") int idEvent , @RequestBody Event event)
    {
        return iEventService.updateEvent(idEvent,event);
    }

    @GetMapping("/get/{id}")
    List<Event> getEvent(@PathVariable("id") int idEvent){
        return iEventService.retrieveEvent(idEvent);
    }

    @GetMapping ("/all")
    List<Event> getAllEvent(){
        return iEventService.getAllEvent();
    }



    @GetMapping ("/allName/{name}")
    List<Event> getEventbyName(@PathVariable  String name){
        return iEventService.getEventByName(name);
    }

    @GetMapping ("/lieus/{lieu}")
    List<Event> getEventByLieu(@PathVariable  String lieu){
        return iEventService.getEventByLieu(lieu);
    }


    @PutMapping("assignInvit/{idi}/{name}")
    public void assignInvit(@PathVariable("idi") int idinvitation,@PathVariable("name") String name)
            throws Exception {
        Invitation invitation = invitationRepository.findById(idinvitation).get();
        iEventService.affecterInvitationToEvenment(invitation,name);
    }
}