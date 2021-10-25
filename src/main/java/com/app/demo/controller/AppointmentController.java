package com.app.demo.controller;

import com.app.demo.dto.AppointmentDto;
import com.app.demo.service.AppointmentService;
import com.app.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDto> getAvailableTimetableForToday() {
        return appointmentService.getAvailableTimetable(Calendar.getInstance().getTime());
    }

    @GetMapping("/{date}")
    public List<AppointmentDto> getAvailableTimetableForDate(@PathVariable("date") String date) {
        return appointmentService.getAvailableTimetable(DateUtil.parseDate(date));
    }


}
