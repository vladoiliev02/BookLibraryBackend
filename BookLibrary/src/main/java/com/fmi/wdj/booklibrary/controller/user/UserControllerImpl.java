package com.fmi.wdj.booklibrary.controller.user;

import com.fmi.wdj.booklibrary.dto.user.UserInfoDto;
import com.fmi.wdj.booklibrary.dto.user.UserDto;
import com.fmi.wdj.booklibrary.dto.user.UserInfoUpdateDto;
import com.fmi.wdj.booklibrary.mapper.user.UserMapper;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.model.user.UserInfo;
import com.fmi.wdj.booklibrary.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserControllerImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    @GetMapping("/admin/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
            .stream()
            .map(userMapper::toUserDto)
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username, Principal principal) {
        if (username.equals(principal.getName()) || userService.isAdmin(principal.getName())) {
            User result = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("User %s, not found.", username)));
            return userMapper.toUserDto(result);
        }
        throw new IllegalArgumentException("You can only see your own personal details.");
    }

    @Override
    @GetMapping("/info/{username}")
    public UserInfoDto getUserInfo(@PathVariable String username, Principal principal) {
        if (username.equals(principal.getName()) || userService.isAdmin(principal.getName())) {
            UserInfo userInfo = userService.getUserInfo(username);
            return userMapper.toUserInfoDto(userInfo);
        }
        throw new IllegalArgumentException("You can only see your own personal details.");
    }

    @Override
    @PutMapping("/info")
    public UserInfoDto updateUserInfo(@RequestBody UserInfoUpdateDto userInfoUpdateDto, Principal principal) {
        UserInfo newInfo = userMapper.fromUserInfoUpdateDto(userInfoUpdateDto);

        User result = userService.updateInfo(principal.getName(), newInfo);

        return userMapper.toUserInfoDto(result.getInfo());
    }

    @Override
    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid UserDto user) {
        User newUser = userService.saveUser(userMapper.fromUserDto(user));
        return userMapper.toUserDto(newUser);
    }

    @Override
    @PutMapping("/admin")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto user) {
        boolean isUpdate = userService.exists(user.getUsername());

        User updatedUser = userMapper.fromUserDto(user);
        if (isUpdate) {
            // Set the id for the details, so they get updated accordingly.
            updatedUser.getInfo().setId(userService.getUserInfo(user.getUsername()).getId());
        }
        userService.saveUser(updatedUser);

        UserDto result = userMapper.toUserDto(updatedUser);
        return isUpdate ? new ResponseEntity<>(result, HttpStatus.OK)
            : new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/admin/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.removeUser(username);
    }
}
