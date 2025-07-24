package com.truemed.Abs.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truemed.Abs.Dto.AuthRequest;
import com.truemed.Abs.Dto.CreateSlotRequest;
import com.truemed.Abs.Dto.SlotResponseDto;
import com.truemed.Abs.Dto.SlotStatus;

@Service
public interface AdminService {
	
	public ResponseEntity<String> createSlot(CreateSlotRequest createSlotRequest);

	public void setMaxBooking(int maxBookings, Long slotId);

	public void enableOrDisableSlot(SlotStatus slotStatus,Long slotId);

	public  List<SlotResponseDto>  viewBookedSlots();

	public List<SlotResponseDto> viewAllSlots();

	public List<SlotResponseDto> viewBookedSlots(LocalDate date);

	
}
