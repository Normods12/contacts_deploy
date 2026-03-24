package com.contacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponseDto {

    private long totalUsers;
    private long totalContacts;
    private long groupCount;

}
