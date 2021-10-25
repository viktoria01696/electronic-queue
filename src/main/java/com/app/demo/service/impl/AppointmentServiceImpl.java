package com.app.demo.service.impl;

import com.app.demo.dao.AppointmentRepository;
import com.app.demo.dto.AppointmentDto;
import com.app.demo.entity.Appointment;
import com.app.demo.entity.User;
import com.app.demo.enums.Status;
import com.app.demo.exception.*;
import com.app.demo.service.AppointmentService;
import com.app.demo.service.UserService;
import com.app.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final SimpleDateFormat formatForTime = new SimpleDateFormat("k:m:s:S");

    @Value("${appointment.duration}")
    private int duration;

    @Value("${appointment.start-time}")
    private int startTime;

    @Value("${appointment.end-time}")
    private int endTime;

    @Value("${appointment.registration-period-in-days}")
    private int registrationPeriod;

    @Value("${appointment.time-for-confirmation}")
    private int timeForConfirmation;

    private AppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //расписание на выбранную дату
    @Override
    @Transactional(readOnly = true)
    public ArrayList<AppointmentDto> getAvailableTimetable(Date date) {
        checkDateForTimetable(date);
        HashMap<String, AppointmentDto> timetableForDate = createTimetableForDate(date);
        Date dateEnd = DateUtil.getDatePlusDay(date);
        List<Appointment> bookedAppointments = appointmentRepository.findForDate(
                date, dateEnd, Status.REGISTERED, getAppointmentConstraintDate());
        if (bookedAppointments != null) {
            List<String> bookedKeys = bookedAppointments.stream()
                    .map(m -> formatForTime.format(m.getDate()))
                    .collect(Collectors.toList());
            bookedKeys.forEach(timetableForDate::remove);
        }
        timetableForDate.values().forEach(m -> System.out.println(m.getDate()));
        ArrayList<AppointmentDto> appointments = new ArrayList<>(timetableForDate.values());
        appointments.sort(Comparator.comparing(AppointmentDto::getDate));
        return appointments;
    }

    //все слоты с подтвержденной бронью для активного пользователя
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> getUserAppointments(String login) {
        Long id = userService.findUserEntityByLogin(login).getId();
        List<Appointment> appointments = appointmentRepository.findByUserIdAndStatus(
                id, Status.getConfirmedStatuses());
        return appointments.stream().map(m -> new AppointmentDto(m.getDate()))
                .collect(Collectors.toList());
    }

    //бронирование временного слота и получение ссылки на подтверждение
    @Override
    @Transactional
    public String addUserAppointment(String login, Date date) {
        appointmentService.chekDateForAppointment(date);
        String code = UUID.randomUUID().toString();
        User user = userService.findUserEntityByLogin(login);
        Appointment appointment = new Appointment(date, code, user,
                Status.REGISTERED);
        if (user.getAppointmentList() == null) {
            user.setAppointmentList(new ArrayList<>());
        }
        user.getAppointmentList().add(appointment);
        appointmentRepository.save(appointment);
        return "http://localhost:8080/user/booking-confirmation/" + code;
    }

    //проверка, что выбранная для бронирования дата по-прежнему доступна
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void chekDateForAppointment(Date date) {
        Appointment appointment = appointmentRepository.findByDateAndStatus(
                date, Status.REGISTERED, getAppointmentConstraintDate());
        if (appointment != null) {
            throw new UnavailableDateException();
        }
    }

    //подтверждение брони по ссылке
    @Override
    @Transactional
    public void confirmAppointment(String code) {
        Appointment appointment = appointmentRepository
                .findByConfirmationCode(code, Status.REGISTERED, getAppointmentConstraintDate());
        if (appointment == null) {
            throw new BadConfirmationLinkException();
        }
        appointment.setStatus(Status.CONFIRMED);
        appointmentRepository.save(appointment);
    }

    //аннулирование записи по логину (для юзера)
    @Override
    @Transactional
    public void deleteAppointmentByUserLogin(String login, Date date) {
        Appointment appointment = appointmentRepository.findByUserAndDateAndStatus(
                userService.findUserEntityByLogin(login), date, Status.CONFIRMED);
        if (appointment == null) {
            throw new AppointmentChangingException();
        }
        appointmentRepository.delete(appointment);
    }

    //аннулирование записи по фамилии (для админа)
    @Override
    @Transactional
    public void deleteAppointmentByUserLastname(String lastname, Date date) {
        Appointment appointment = appointmentRepository.findByUserLastnameAndDateAndStatus(
                lastname, date, Status.CONFIRMED);
        if (appointment == null) {
            throw new AppointmentChangingException();
        }
        appointmentRepository.delete(appointment);
    }

    //подтверждение явки пользователя
    @Override
    @Transactional
    public void confirmUserArrival(String lastname, Date date) {
        appointmentService.changeAppointmentStatus(lastname, date,
                Status.CONFIRMED, Status.MARKED);
    }

    //завершение сеанса пользователя
    @Override
    @Transactional
    public void finishAppointment(String lastname, Date date) {
        appointmentService.changeAppointmentStatus(lastname, date,
                Status.PROCESSED, Status.FINISHED);
    }

    //изменение статуса записи
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeAppointmentStatus(String lastname, Date date,
                                        Status currentStatus, Status nextStatus) {
        Appointment appointment = appointmentRepository.findByUserLastnameAndDateAndStatus(
                lastname, date, currentStatus);
        if (appointment == null) {
            throw new AppointmentChangingException();
        }
        appointment.setStatus(nextStatus);
        appointmentRepository.save(appointment);
    }

    //поиск ближайшей активной записи
    @Override
    @Transactional(readOnly = true)
    public AppointmentDto getNextAppointment() {
        Appointment appointment = appointmentRepository.findClosestAppointment(
                Calendar.getInstance().getTime(), Status.getConfirmedStatusesId());
        if (appointment == null) {
            throw new EmptyNextAppointmentException();
        }
        return new AppointmentDto(appointment.getDate());
    }

    //получение записи на настоящий момент времени
    @Override
    @Transactional(readOnly = true)
    public Appointment getCurrentAppointment(Date date) {
        return appointmentRepository.findByDate(date);
    }

    //приглашение следующего гостя
    @Override
    @Transactional
    public AppointmentDto startNextAppointment() {
        Appointment appointment = appointmentRepository.findByLongestWaitingTime(
                Calendar.getInstance().getTime(), Status.MARKED.ordinal());
        if (appointment == null) {
            throw new EmptyNextAppointmentException();
        }
        appointment.setStatus(Status.PROCESSED);
        appointmentRepository.save(appointment);
        return new AppointmentDto(appointment.getDate());
    }

    //удаление неподтвержденных записей во время регулярной проверки
    @Override
    @Transactional
    public void findAndDeleteUnconfirmedAppointments() {
        List<Appointment> appointments = appointmentRepository.findUnconfirmed(
                Status.REGISTERED, getAppointmentConstraintDate());
        if (!appointments.isEmpty()) {
            appointmentRepository.deleteAll(appointments);
        }
    }


    //проверка принадлежности даты доступному для брони диапазону
    private void checkDateForTimetable(Date date) {
        Calendar firstDateForCheck = DateUtil.removeTime(Calendar.getInstance().getTime());
        Calendar lastDateForCheck = DateUtil.removeTime(Calendar.getInstance().getTime());
        lastDateForCheck.add(Calendar.DATE, registrationPeriod);
        if (date.after(lastDateForCheck.getTime())
                || date.before(firstDateForCheck.getTime())) {
            throw new TimetableNotFoundException();
        }
    }

    //создание расписания на конкретную дату без фильтрации недоступных слотов
    private HashMap<String, AppointmentDto> createTimetableForDate(Date date) {
        HashMap<String, AppointmentDto> timetableForDate = new HashMap<>();
        Calendar startWorking = DateUtil.createDate(date, startTime);
        Calendar endWorking = DateUtil.createDate(date, endTime);
        Date appointmentTime = startWorking.getTime();
        Date currentMoment = Calendar.getInstance().getTime();
        while (currentMoment.after(appointmentTime)) {
            startWorking.add(Calendar.MINUTE, duration);
            appointmentTime = startWorking.getTime();
        }
        while (appointmentTime.before(endWorking.getTime())) {
            timetableForDate.put(formatForTime.format(appointmentTime),
                    new AppointmentDto(appointmentTime));
            startWorking.add(Calendar.MINUTE, duration);
            appointmentTime = startWorking.getTime();
        }
        return timetableForDate;
    }

    //генерация даты для фильтрации не подтвержденных в течение 15 минут заявок
    private Date getAppointmentConstraintDate() {
        Calendar constraintDate = Calendar.getInstance();
        constraintDate.add(Calendar.MINUTE, -timeForConfirmation);
        return constraintDate.getTime();
    }


}
