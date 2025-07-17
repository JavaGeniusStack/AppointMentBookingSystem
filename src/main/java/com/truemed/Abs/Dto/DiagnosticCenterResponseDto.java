package com.truemed.Abs.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.truemed.Abs.Entity.DiagnosticCenter;
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
public class DiagnosticCenterResponseDto {

    private Long id;
    private String centerName;
    private String location;
    private String contactInfo;
    private int rating;
    private List<String> reviews;
    private List<Long> bookedSlotIds;  

    public static DiagnosticCenterResponseDto fromEntity(DiagnosticCenter diagnosticCenter) {
        return DiagnosticCenterResponseDto.builder()
                .id(diagnosticCenter.getId())
                .centerName(diagnosticCenter.getCenterName())
                .location(diagnosticCenter.getLocation())
                .contactInfo(diagnosticCenter.getContactInfo())
                .rating(diagnosticCenter.getRating())
                .reviews(diagnosticCenter.getReviews())
                .bookedSlotIds(diagnosticCenter.getBookedSlots().stream()
                    .map(Slot::getId)  
                    .collect(Collectors.toList()))
                .build();
    }
}
