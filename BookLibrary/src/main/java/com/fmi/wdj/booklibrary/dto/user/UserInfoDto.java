package com.fmi.wdj.booklibrary.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserInfoDto {
    @NotNull(message = "E-mail cannot be null.")
    @NotEmpty(message = "E-mail cannot be empty.")
    @NotBlank(message = "E-mail cannot be blank.")
    private String email;

    String firstName;

    String lastName;

    private String phoneNumber;

    private String country;

    private String city;

    private String street;
}
