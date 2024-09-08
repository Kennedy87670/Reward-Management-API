package com.rewardManagement.demo.auth.service;

import com.rewardManagement.demo.auth.entity.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    UserDto createUser(UserDto userDto);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto updateUser(Integer userId, UserDto userDto);

    UserDto updateUserRole(Integer userId, String newRole);

    void deleteUser(Integer userId);

}
