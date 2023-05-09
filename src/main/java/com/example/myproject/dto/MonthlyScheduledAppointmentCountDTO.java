package com.example.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyScheduledAppointmentCountDTO {
    private int year;
    private int month;
    private long count;
}
