package com.truemed.Abs.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.truemed.Abs.Entity.Slot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponseDto {

    private Long id;
    private LocalDate date;
    private LocalTime slotStartTime;
    private LocalTime endTime;
    private int bookingCount;
    private int maxBooking;
    private SlotStatus isSlotEnabled;
    private Long diagnosticCenterId; 

    public static SlotResponseDto fromEntity(Slot slot) {
        return SlotResponseDto.builder()
                .id(slot.getId())
                .date(slot.getDate())
                .slotStartTime(slot.getSlotStartTime())
                .endTime(slot.getEndTime())
                .bookingCount(slot.getBookingcount())
                .maxBooking(slot.getMaxBooking())
                .isSlotEnabled(slot.getIsSlotEnabled())
                .diagnosticCenterId(slot.getDiagnosticCenter().getId()) 
                .build();
    }
}
