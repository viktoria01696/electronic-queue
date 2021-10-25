package com.app.demo.controller;

import com.app.demo.dto.AppointmentDto;
import com.app.demo.service.AppointmentService;
import com.app.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AppointmentService appointmentService;

    @PostMapping("/confirm")
    public void confirmUserArrival(@RequestParam("lastname") String lastname,
                                   @RequestParam("date") String date, @RequestParam("time") String time) {
        appointmentService.confirmUserArrival(lastname, DateUtil.parseDate(date, time));
    }

    @PostMapping("/finish")
    public void finishUserAppointment(@RequestParam("lastname") String lastname, @RequestParam("date") String date,
                                      @RequestParam("time") String time) {
        appointmentService.finishAppointment(lastname, DateUtil.parseDate(date, time));
    }

    @DeleteMapping("/cancel")
    public void cancelAppointment(@RequestParam("lastname") String lastname, @RequestParam("date") String date,
                                  @RequestParam("time") String time) {
        appointmentService.deleteAppointmentByUserLastname(lastname, DateUtil.parseDate(date, time));
    }

    @GetMapping("/next-appointment")
    public AppointmentDto getNextAppointment() {
        return appointmentService.getNextAppointment();
    }

    @PostMapping("/invite-next")
    public AppointmentDto inviteNextGuest() {
        return appointmentService.startNextAppointment();
    }

}
