package com.wallmart.calendar.repository;

import com.wallmart.calendar.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer>
{

}
