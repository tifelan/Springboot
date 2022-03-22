package com.phoenix.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String address;
}
