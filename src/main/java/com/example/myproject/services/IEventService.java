package com.example.myproject.services;


import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import lombok.extern.java.Log;

import java.util.List;

public interface IEventService {

    int addEvent(Event event);

   Event updateEvent(int idEvent, Event event);

    void removeEvent(int idEvent);

    List<Event> retrieveEvent(int idEvent);

   List<Event> getAllEvent();

    List<Event> getEventByName(String name);


    List<Event> getEventByLieu(String lieu);

    public void affecterInvitationToEvenment(Invitation ie, String name);









}
