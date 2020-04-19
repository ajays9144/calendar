package com.wallmart.calendar.service;

import com.wallmart.calendar.entity.Event;
import com.wallmart.calendar.entity.EventUser;
import com.wallmart.calendar.repository.EventUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventUserServices {

    @Autowired
    EventUserRepository eventUserRepository;

}
