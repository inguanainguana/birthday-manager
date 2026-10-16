package ru.komogorova.birthday_manager.service;

import ru.komogorova.birthday_manager.dto.UserDto;
import ru.komogorova.birthday_manager.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findByUsername(String username);
    User findByEmail (String email);
    List<UserDto> findAllUsers();
}
