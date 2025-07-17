package com.truemed.Abs.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBookingRequest {
	private LocalDate date;
	private LocalTime slotStartTime;
	private Long slotId;
	private Long userId;
}
