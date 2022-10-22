package com.tenpo.challenge.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.challenge.exceptions.ControllerHandlerException;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.dtos.UserRequestDto;
import com.tenpo.challenge.models.dtos.UserResponseDto;
import com.tenpo.challenge.services.UserService;
import com.tenpo.challenge.utils.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/tenpo/user")
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(ControllerHandlerException.class);
    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto request)
            throws ValidationException, JsonProcessingException {
        log.info(String.format("Event: %s - Request: %s", "createUser", request.toString()));
        UserResponseDto response = userService.createUser(request);
        log.info(String.format("Event: %s - Response: %s", "createUser", response.toString()));
        return response;
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto request)
            throws ValidationException, JsonProcessingException {
        log.info(String.format("Event: %s - Request: %s", "createUser", request.toString()));
        UserResponseDto response = userService.login(request);
        log.info(String.format("Event: %s - Response: %s", "createUser", response.toString()));
        return response;
    }
}