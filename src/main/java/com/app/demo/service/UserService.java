package com.app.demo.service;

import com.app.demo.dto.UserDto;
import com.app.demo.entity.User;

public interface UserService {

    UserDto getNewUser();

    UserDto findUserByLogin(String login);

    void saveUser(UserDto userDto);

    User findUserEntityByLogin(String login);
}
