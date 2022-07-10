package com.fmi.wdj.booklibrary.dto.user;

import com.fmi.wdj.booklibrary.security.roles.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "The username should be between 5 and 20 characters long.")
    private String username;

    @NotNull
    private UserInfoDto info;

    @NotNull
    private String password;

    @NotNull
    private Boolean isEnabled;

    @NotNull
    private Role role;
}
