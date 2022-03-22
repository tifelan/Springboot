package com.phoenix.data.dto;

import lombok.Data;


@Data

public class AppUserRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String address;
}
