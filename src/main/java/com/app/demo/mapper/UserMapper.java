package com.app.demo.mapper;

import com.app.demo.dao.AppointmentRepository;
import com.app.demo.dao.RoleRepository;
import com.app.demo.dao.UserRepository;
import com.app.demo.dto.UserDto;
import com.app.demo.entity.Appointment;
import com.app.demo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(ModelMapper mapper, AppointmentRepository appointmentRepository,
                      RoleRepository roleRepository, UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        super(mapper, User.class, UserDto.class);
        this.appointmentRepository = appointmentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setAppointmentIdList))
                .addMappings(m -> m.skip(UserDto::setPassword))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setRole))
                .addMappings(m -> m.skip(User::setCreateDate))
                .addMappings(m -> m.skip(User::setPassword))
                .addMappings(m -> m.skip(User::setAppointmentList))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFieldsInDtoConverter(User source, UserDto destination) {
        destination.setAppointmentIdList(Objects.isNull(source.getAppointmentList()) ?
                null : source.getAppointmentList().stream().map(Appointment::getId).collect(Collectors.toList()));
        destination.setPassword("*your password is encoded*");
    }

    @Override
    public void mapSpecificFieldsInEntityConverter(UserDto source, User destination) {
        destination.setRole(roleRepository.findRoleByName("USER"));
        destination.setCreateDate(userRepository.findByLogin(source.getLogin()) == null ?
                Calendar.getInstance().getTime() : userRepository.findByLogin(source.getLogin()).getCreateDate());
        destination.setPassword(passwordEncoder.encode(source.getPassword()));
        destination.setAppointmentList(Objects.isNull(source.getAppointmentIdList()) ?
                null : source.getAppointmentIdList().stream().map(appointmentRepository::findById).map(m -> m.orElse(null))
                .collect(Collectors.toList()));
    }
}
