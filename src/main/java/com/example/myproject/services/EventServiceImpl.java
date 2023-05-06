package com.example.myproject.services;


import com.example.myproject.entities.Event;
import com.example.myproject.entities.Invitation;
import com.example.myproject.repository.EventRepository;
import com.example.myproject.repository.IInvitationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("evenment")
@Slf4j

public class EventServiceImpl implements IEventService{

    @Autowired
      EventRepository   eventRepository;

    @Autowired
    IInvitationRepository invitationRepository;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public int addEvent(Event event) {
        eventRepository.save(event);
        log.info(event+"Ajouter avec succee ");
        return event.getIdEvent();

    }

    @Override
    public Event updateEvent(int idEvent, Event event) {
        List<Event> newEvent = eventRepository.findByIdEvent(idEvent);
        if (event.getName()!= null)
            newEvent.get(0).setName(event.getName());
        return eventRepository.save(newEvent.get(0));

    }

    @Override
    public void removeEvent(int idEvent) {
        eventRepository.deleteById(idEvent);

    }

    @Override
    public List<Event> retrieveEvent(int idEvent) {
        return eventRepository.findByIdEvent(idEvent);
    }

    @Override
    public List<Event> getAllEvent() {
        List<Event> evenments = new ArrayList<>();
       eventRepository.findAll().forEach(evenments::add);
        return evenments;
    }

    @Override
    public List<Event> getEventByName(String name) {
        List<Event> evenments = new ArrayList<>();
        eventRepository.findByName(name).forEach(evenments::add);
        return evenments;

    }

    @Override
    public List<Event> getEventByLieu(String lieu) {
        List<Event> evenments = new ArrayList<>();
        eventRepository.findByLieu(lieu).forEach(evenments::add);
        return evenments;

    }

    @Override
    public void affecterInvitationToEvenment(Invitation ie, String name) {
        Event e = eventRepository.retrieveEventByName(name);
        Integer total = eventRepository.countI(e.getIdEvent());
        if (total < 200) {
            ie.setEvent(e);
            invitationRepository.save(ie);
        }else{
            log.info("event "+e.getName()+"a atteint la limit  !");
        }
    }
}
