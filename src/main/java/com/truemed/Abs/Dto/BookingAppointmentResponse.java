package com.truemed.Abs.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingAppointmentResponse {
	private Long userId;
	private LocalDate date;
	private LocalTime slotStartTime;
	private Long slotId;
	private BookingStatus bookingStatus;
	private String message;
}
