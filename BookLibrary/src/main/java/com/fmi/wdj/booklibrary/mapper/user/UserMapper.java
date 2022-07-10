package com.fmi.wdj.booklibrary.mapper.user;

import com.fmi.wdj.booklibrary.dto.user.UserInfoDto;
import com.fmi.wdj.booklibrary.dto.user.UserDto;
import com.fmi.wdj.booklibrary.dto.user.UserInfoUpdateDto;
import com.fmi.wdj.booklibrary.model.user.User;
import com.fmi.wdj.booklibrary.model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setUsername(user.getUsername());
        dto.setInfo(toUserInfoDto(user.getInfo()));
        dto.setRole(user.getRole());
        dto.setPassword(user.getPassword());
        dto.setIsEnabled(user.getIsEnabled());

        return dto;
    }

    public UserInfoDto toUserInfoDto(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getEmail(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getCountry(),
                userInfo.getCity(),
                userInfo.getStreet()
        );
    }

    public User fromUserDto(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setInfo(fromUserInfoDto(userDto.getInfo()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        user.setIsEnabled(userDto.getIsEnabled());

        return user;
    }

    public UserInfo fromUserInfoDto(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(userInfoDto.getEmail());
        userInfo.setFirstName(userInfoDto.getFirstName());
        userInfo.setLastName(userInfoDto.getLastName());
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        userInfo.setCountry(userInfoDto.getCountry());
        userInfo.setCity(userInfoDto.getCity());
        userInfo.setStreet(userInfoDto.getStreet());

        return userInfo;
    }

    public UserInfo fromUserInfoUpdateDto(UserInfoUpdateDto infoUpdateDto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(infoUpdateDto.getFirstName());
        userInfo.setLastName(infoUpdateDto.getLastName());
        userInfo.setPhoneNumber(infoUpdateDto.getPhoneNumber());
        userInfo.setCity(infoUpdateDto.getCity());
        userInfo.setCountry(infoUpdateDto.getCountry());
        userInfo.setStreet(infoUpdateDto.getStreet());

        return userInfo;
    }
}
