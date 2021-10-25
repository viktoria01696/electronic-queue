package com.app.demo.controller;

import com.app.demo.dto.UserDto;
import com.app.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public UserDto getRegistrationForm() {
        return userService.getNewUser();
    }

    @PostMapping
    public void submitRegistrationForm(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

}
