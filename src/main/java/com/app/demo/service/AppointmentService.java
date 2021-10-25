package com.app.demo.service;

import com.app.demo.dto.AppointmentDto;
import com.app.demo.entity.Appointment;
import com.app.demo.enums.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AppointmentService {

    ArrayList<AppointmentDto> getAvailableTimetable(Date date);

    List<AppointmentDto> getUserAppointments(String login);

    String addUserAppointment(String login, Date date);

    void confirmAppointment(String code);

    void deleteAppointmentByUserLogin(String login, Date date);

    void deleteAppointmentByUserLastname(String lastname, Date date);

    void chekDateForAppointment(Date date);

    void confirmUserArrival(String lastname, Date date);

    void finishAppointment(String lastname, Date date);

    void changeAppointmentStatus(String lastname, Date date,
                                 Status currentStatus, Status nextStatus);

    AppointmentDto getNextAppointment();

    Appointment getCurrentAppointment(Date date);

    AppointmentDto startNextAppointment();

    void findAndDeleteUnconfirmedAppointments();


}
