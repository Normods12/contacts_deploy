package com.contacts.dto;

import lombok.Data;

@Data
public class ModifyContactDto {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String company;
    private String notes;
    private Boolean isFavourite;
}
