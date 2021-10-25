package com.app.demo.service.impl;

import com.app.demo.dao.UserRepository;
import com.app.demo.dto.UserDto;
import com.app.demo.entity.User;
import com.app.demo.exception.UserNotFoundException;
import com.app.demo.mapper.UserMapper;
import com.app.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto getNewUser() {
        return new UserDto();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByLogin(String login) {
        return userMapper.toDto(userService.findUserEntityByLogin(login));
    }

    @Override
    @Transactional
    public void saveUser(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User findUserEntityByLogin(String login) {
        return Optional.of(userRepository.findByLogin(login)).orElseThrow(UserNotFoundException::new);
    }

}
