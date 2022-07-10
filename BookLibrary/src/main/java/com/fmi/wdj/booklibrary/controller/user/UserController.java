package com.fmi.wdj.booklibrary.controller.user;

import com.fmi.wdj.booklibrary.dto.user.UserInfoDto;
import com.fmi.wdj.booklibrary.dto.user.UserDto;
import com.fmi.wdj.booklibrary.dto.user.UserInfoUpdateDto;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface UserController {
    List<UserDto> getAllUsers();

    UserDto getUser(String username, Principal principal);

    UserInfoDto getUserInfo(String username, Principal principal);

    UserInfoDto updateUserInfo(UserInfoUpdateDto userInfoDto, Principal principal);

    UserDto addUser(UserDto user);

    ResponseEntity<UserDto> updateUser(UserDto user);

    void deleteUser(String username);
}
