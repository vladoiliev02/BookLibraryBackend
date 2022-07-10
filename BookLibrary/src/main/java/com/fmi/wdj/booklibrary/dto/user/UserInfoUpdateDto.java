package com.fmi.wdj.booklibrary.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoUpdateDto {
    String firstName;

    String lastName;

    private String phoneNumber;

    private String country;

    private String city;

    private String street;
}
