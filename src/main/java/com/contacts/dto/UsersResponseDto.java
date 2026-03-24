package com.contacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersResponseDto {

    private long id;
    private String firstName;
    private String email;

}
