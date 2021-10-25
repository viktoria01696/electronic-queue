package com.app.demo.scheduler;

import com.app.demo.entity.Appointment;
import com.app.demo.enums.Status;
import com.app.demo.service.AppointmentService;
import com.app.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final AppointmentService appointmentService;

    @Scheduled(cron = "${appointment.cron}")
    public void checkAppointment() {
        Date date = DateUtil.removeSeconds(Calendar.getInstance().getTime()).getTime();
        Appointment appointment = appointmentService.getCurrentAppointment(date);
        if (appointment != null && appointment.getStatus().equals(Status.CONFIRMED)) {
            appointmentService.deleteAppointmentByUserLogin(appointment.getUser().getLogin(), date);
        }
    }

    @Scheduled(initialDelayString = "PT02H", fixedRateString = "PT02H")
    public void deleteNotConfirmedAppointment() {
        appointmentService.findAndDeleteUnconfirmedAppointments();
    }

}
