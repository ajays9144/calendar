package com.wallmart.calendar.service;

import com.wallmart.calendar.entity.Event;
import com.wallmart.calendar.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServices {

    @Autowired
    EventRepository eventRepository;

    public void insertEvent(Event event) throws DataAccessException {
        eventRepository.save(event);
    }

    public List<Event> getEvents() throws DataAccessException {
        return eventRepository.findAll();
    }

    public Event findById(int id) throws DataAccessException {
        return eventRepository.findById(id).get();
    }
}
