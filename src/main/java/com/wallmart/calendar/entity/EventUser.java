package com.wallmart.calendar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "event_user")
public class EventUser implements Serializable {
    /**
     * State->  0:- Default
     * 1:- Accept
     * 2:- Decline
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @JsonProperty("event_id")
    private Event event;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("state")
    private int state;
}
