package com.app.demo.dao;

import com.app.demo.entity.Appointment;
import com.app.demo.entity.User;
import com.app.demo.enums.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    //занятые слоты на определенную дату
    @Query("from Appointment ap " +
            "where ap.date between :startDate and :endDate " +
            "and (ap.status <> :status or ap.createDate >= :allowedCreationDate)")
    List<Appointment> findForDate(@Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
                                  @Param("status") Status status,
                                  @Param("allowedCreationDate") Date allowedCreationDate);

    //активная бронь для пользователя
    @Query("from Appointment ap " +
            "where ap.id = :userId " +
            "and ap.status in :confirmedStatuses " +
            "order by ap.date")
    List<Appointment> findByUserIdAndStatus(@Param("userId") Long userId,
                                            @Param("confirmedStatuses") Status[] confirmedStatuses);

    //проверка доступности выбранного слота
    @Query("from Appointment ap " +
            "where ap.date = :date " +
            "and (ap.status <> :status or ap.createDate>= :allowedCreationDate)")
    Appointment findByDateAndStatus(@Param("date") Date date,
                                    @Param("status") Status status,
                                    @Param("allowedCreationDate") Date allowedCreationDate);

    //проверка ссылки для подтверждения брони
    @Query("from Appointment ap " +
            "where ap.confirmationCode = :code " +
            "and (ap.status <> :status or ap.createDate>= :allowedCreationDate)")
    Appointment findByConfirmationCode(@Param("code") String code,
                                       @Param("status") Status status,
                                       @Param("allowedCreationDate") Date allowedCreationDate);

    //поиск записи для удаления
    Appointment findByUserAndDateAndStatus(User user, Date date, Status status);

    //поиск записи для подтверждения явки пользователя/удаления админом
    @Query("from Appointment ap " +
            "where ap.user.lastname = :lastname " +
            "and ap.date = :date " +
            "and ap.status = :status")
    Appointment findByUserLastnameAndDateAndStatus(@Param("lastname") String lastname,
                                                   @Param("date") Date date,
                                                   @Param("status") Status status);

    //получение ближайшей активной записи
    @Query(value = "select * " +
            "from appointment " +
            "where appointment.date >= :date " +
            "and appointment.status_id in :id " +
            "order by appointment.date asc " +
            "limit 1",
            nativeQuery = true)
    Appointment findClosestAppointment(@Param("date") Date date, @Param("id") int[] id);

    Appointment findByDate(Date date);

    //получение пользователя, подтвердившего свою явку и ожидающего приема дольше всех
    @Query(value = "select * " +
            "from appointment " +
            "where appointment.date <= :date " +
            "and appointment.status_id = :statusId " +
            "order by (:date - appointment.date) desc " +
            "limit 1",
            nativeQuery = true)
    Appointment findByLongestWaitingTime(@Param("date") Date date, @Param("statusId") int statusId);

    //поиск неподтвержденных записей
    @Query("from Appointment ap " +
            "where ap.status = :status " +
            "and ap.createDate < :allowedCreationDate")
    List<Appointment> findUnconfirmed(@Param("status") Status status,
                                      @Param("allowedCreationDate") Date allowedCreationDate);
}
