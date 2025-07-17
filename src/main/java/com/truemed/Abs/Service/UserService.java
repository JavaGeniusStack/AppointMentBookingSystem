package com.truemed.Abs.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.truemed.Abs.Dto.BookingAppointmentResponse;
import com.truemed.Abs.Dto.CreateBookingRequest;
import com.truemed.Abs.Dto.SlotResponseDto;
import com.truemed.Abs.Dto.UpdateProfileRequest;
import com.truemed.Abs.Dto.User;
import com.truemed.Abs.Entity.CustomUser;

@Service
public interface UserService {
	public ResponseEntity<String> registerUser(String email,String mobile);
	public Boolean loginByOtpOrPassword(User user);
	public ResponseEntity profileSetUp(UpdateProfileRequest updateProfileRequest);
	public BookingAppointmentResponse bookSlot(CreateBookingRequest createBookingRequest);
	public List<SlotResponseDto> viewAllSlots();
	public ResponseEntity cancelBooking(Long id);
}
