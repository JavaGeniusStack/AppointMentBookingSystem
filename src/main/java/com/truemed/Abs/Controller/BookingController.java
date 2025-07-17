package com.truemed.Abs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.BookingAppointmentResponse;
import com.truemed.Abs.Dto.CreateBookingRequest;
import com.truemed.Abs.Service.UserService;

@RequestMapping("/api/booking")
@RestController
public class BookingController {
	@Autowired
	UserService userService;

	@PostMapping("/bookAppointment")
	public ResponseEntity bookSlot(@RequestBody CreateBookingRequest createBookingRequest) {

		BookingAppointmentResponse bookedSlot = userService.bookSlot(createBookingRequest);
		if (bookedSlot == null) {

		}
		return ResponseEntity.ok(bookedSlot);
	}

	@PostMapping("/cancelAppointment")
	public ResponseEntity cancelAppointment(@RequestParam Long  id) {
		
		return userService.cancelBooking(id);
	}
}
