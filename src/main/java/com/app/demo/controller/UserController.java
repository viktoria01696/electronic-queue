package com.app.demo.controller;

import com.app.demo.dto.AppointmentDto;
import com.app.demo.dto.UserDto;
import com.app.demo.service.AppointmentService;
import com.app.demo.service.UserService;
import com.app.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping("/my-profile")
    public UserDto getCurrentProfile(Principal principal) {
        return userService.findUserByLogin(principal.getName());
    }

    @PostMapping("/my-profile")
    public void submitUserForm(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @GetMapping("/my-appointments")
    public List<AppointmentDto> getUserAppointments(Principal principal) {
        return appointmentService.getUserAppointments(principal.getName());
    }

    @PostMapping("/my-appointments")
    public void addUserAppointment(Principal principal, @RequestParam("date") String date,
                                   @RequestParam("time") String time) {
        System.out.println(
                appointmentService.addUserAppointment(principal.getName(), DateUtil.parseDate(date, time)));
    }

    @PostMapping("/booking-confirmation/{code}")
    public void confirmAppointment(@PathVariable("code") String code) {
        appointmentService.confirmAppointment(code);
    }

    @DeleteMapping("/my-appointments")
    public void cancelAppointment(Principal principal, @RequestParam("date") String date,
                                  @RequestParam("time") String time) {
        appointmentService.deleteAppointmentByUserLogin(principal.getName(), DateUtil.parseDate(date, time));
    }

}
