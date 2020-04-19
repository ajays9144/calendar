package com.wallmart.calendar.controller;

import com.wallmart.calendar.entity.Event;
import com.wallmart.calendar.entity.EventUser;
import com.wallmart.calendar.rest.BindingErrorsResponse;
import com.wallmart.calendar.service.EventServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    EventServices eventServices;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RequestEntity<Collection<Event>> getEvent() {
        Collection<Event> events = eventServices.getEvents();
        if (events.isEmpty()) {
            return new RequestEntity<Collection<Event>>(events, HttpMethod.GET, URI.create(""));
        }
        return new RequestEntity<Collection<Event>>(events, HttpMethod.GET, URI.create(""));
    }

    /**
     * {
     * 	"start_time":"1587280358",
     * 	"end_time":"1587279938",
     * 	"title":"this is title",
     * 	"owner_id":"1",
     * 	"address":"kainchi chhola bhopal",
     * 	"latitude":23.2771413,
     * 	"longitude":77.4142439,
     * 	"user":[
     *                {
     * 		"user_id":"2"
     *        }, {
     * 		"user_id":"3"
     *        }
     * ]
     * }*/
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public RequestEntity<Event> addEvent(@RequestBody @Valid Event event, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse error = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (event == null)) {
            error.addAllErrors(bindingResult);
            headers.add("error", error.toJSON());
            return new RequestEntity<Event>(headers, HttpMethod.POST, URI.create(""));
        }
        Set<EventUser> user1 = new HashSet<>();
        Set<EventUser> eventUsers = event.getEventUsers();
        for (EventUser event1 : eventUsers) {
            event1.setEvent(event);
            user1.add(event1);
        }
        event.setEventUsers(user1);
        eventServices.insertEvent(event);
        headers.setLocation(ucBuilder.path("api/users/{id}").buildAndExpand(event.getId()).toUri());
        return new RequestEntity<Event>(event, headers, HttpMethod.POST, URI.create(""));
    }


    @RequestMapping(value = "/{eventId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Event> udpateEvent(@PathVariable("eventId") int eventId, @RequestBody @Valid Event event, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (event == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Event>(headers, HttpStatus.BAD_REQUEST);
        }
        Event currentEvent = this.eventServices.findById(eventId);
        if (currentEvent == null) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
        currentEvent.setEndTime(event.getEndTime());
        currentEvent.setStartTime(event.getStartTime());
//        currentEvent.setLocation(event.getLocation());
        currentEvent.setTitle(event.getTitle());
        this.eventServices.insertEvent(currentEvent);
        return new ResponseEntity<Event>(currentEvent, HttpStatus.NO_CONTENT);
    }
}
