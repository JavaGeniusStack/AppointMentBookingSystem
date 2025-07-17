package com.truemed.Abs.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.CreateSlotRequest;
import com.truemed.Abs.Dto.SlotResponseDto;
import com.truemed.Abs.Dto.SlotStatus;
import com.truemed.Abs.Entity.Slot;
import com.truemed.Abs.Service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	AdminService adminService;

	@PostMapping("/createSlots")
	public ResponseEntity createSlot(@RequestBody CreateSlotRequest createSlotRequest) {

		return adminService.createSlot(createSlotRequest);
	}

	@PatchMapping("/setMaxBooking")
	public ResponseEntity setMaxBooking(@RequestParam int maxBookings, @RequestParam Long slotId) {
		adminService.setMaxBooking(maxBookings, slotId);
		return null;
	}

	@PatchMapping("/enableOrDisableSlot")
	public ResponseEntity enableOrDisableSlot(@RequestParam SlotStatus slotStatus, @RequestParam Long slotId) {
		adminService.enableOrDisableSlot(slotStatus, slotId);
		return null;
	}

	@GetMapping("/viewSlots")
	public ResponseEntity viewAllSlots() {
		List<SlotResponseDto> slots = adminService.viewAllSlots();
		if (slots.isEmpty()) {
			return ResponseEntity.badRequest().body("No slots booked");
		} else {
			return ResponseEntity.ok(slots);
		}
	}
	
	@GetMapping("/viewBookedSlots")
	public ResponseEntity viewBookedSlots() {
		List<SlotResponseDto> slots = adminService.viewBookedSlots();
		if (slots.isEmpty()) {
			return ResponseEntity.badRequest().body("No slots booked");
		} else {
			return ResponseEntity.ok(slots);
		}
	}
	
	@GetMapping("/viewBookedSlotsByDate")
	public ResponseEntity viewBookedSlotsByDate(@RequestParam LocalDate date) {
		List<SlotResponseDto> slots = adminService.viewBookedSlots(date);
		if (slots.isEmpty()) {
			return ResponseEntity.badRequest().body("No slots booked for this perticular date");
		} else {
			return ResponseEntity.ok(slots);
		}
	}
}
