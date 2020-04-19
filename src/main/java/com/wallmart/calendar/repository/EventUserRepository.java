package com.wallmart.calendar.repository;

import com.wallmart.calendar.entity.Event;
import com.wallmart.calendar.entity.EventUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventUserRepository extends CrudRepository<EventUser, Integer> {

    List<EventUser> getEventUserBy(Event event, Sort sort);
}
