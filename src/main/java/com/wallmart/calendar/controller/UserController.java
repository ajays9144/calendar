package com.wallmart.calendar.controller;

import com.wallmart.calendar.entity.User;
import com.wallmart.calendar.rest.BindingErrorsResponse;
import com.wallmart.calendar.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserServices userServices;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public RequestEntity<Collection<User>> getEvent() {
        Collection<User> users = userServices.getUsers();
        if (users.isEmpty()) {
            return new RequestEntity<Collection<User>>(users, HttpMethod.GET, URI.create(""));
        }
        return new RequestEntity<Collection<User>>(users, HttpMethod.GET, URI.create(""));
    }

    /**{
     "first_name":"john",
     "last_name":"wick",
     "email":"wick1@mail.com"
     }
     */
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public RequestEntity<User> addEvent(@RequestBody @Valid User user, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse error = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (user == null)) {
            error.addAllErrors(bindingResult);
            headers.add("error", error.toJSON());
            return new RequestEntity<User>(headers, HttpMethod.POST, URI.create(""));
        }
        userServices.addUser(user);
        headers.setLocation(ucBuilder.path("api/users/{id}").buildAndExpand(user.getId()).toUri());
        return new RequestEntity<User>(user, headers, HttpMethod.POST, URI.create(""));
    }
}
