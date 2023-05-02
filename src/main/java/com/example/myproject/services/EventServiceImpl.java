package com.example.myproject.services;


import com.example.myproject.entities.Event;
import com.example.myproject.repository.EventRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Service("evenment")

public class EventServiceImpl implements IEventService{


    final  EventRepository   eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(Event event) {
        eventRepository.save(event);

    }

    @Override
    public Event updateEvent(Long idEvent, Event event) {
        List<Event> newEvent = eventRepository.findByIdEvent(idEvent);
        if (event.getName()!= null)
            newEvent.get(0).setName(event.getName());
        return eventRepository.save(newEvent.get(0));

    }

    @Override
    public void removeEvent(Long idEvent) {
        eventRepository.deleteById(idEvent);

    }

    @Override
    public List<Event> retrieveEvent(Long idEvent) {
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


}
