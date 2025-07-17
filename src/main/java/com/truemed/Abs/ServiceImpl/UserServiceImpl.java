package com.truemed.Abs.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truemed.Abs.Dto.BookingAppointmentResponse;
import com.truemed.Abs.Dto.BookingStatus;
import com.truemed.Abs.Dto.CreateBookingRequest;
import com.truemed.Abs.Dto.CreateSlotRequest;
import com.truemed.Abs.Dto.SlotResponseDto;
import com.truemed.Abs.Dto.SlotStatus;
import com.truemed.Abs.Dto.UpdateProfileRequest;
import com.truemed.Abs.Dto.User;
import com.truemed.Abs.Entity.BookingAppointment;
import com.truemed.Abs.Entity.CustomUser;
import com.truemed.Abs.Entity.DiagnosticCenter;
import com.truemed.Abs.Entity.Slot;
import com.truemed.Abs.Repo.BookingRepo;
import com.truemed.Abs.Repo.DiagnosticCenterRepo;
import com.truemed.Abs.Repo.SlotRepo;
import com.truemed.Abs.Repo.UserRepository;
import com.truemed.Abs.Service.AdminService;
import com.truemed.Abs.Service.UserService;
import com.truemed.Abs.Utility.OtpGenerationUtil;
import com.truemed.Abs.Utility.SlotManagementUtil;

import jakarta.persistence.PessimisticLockException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService, AdminService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	SlotRepo slotRepo;

	@Autowired
	DiagnosticCenterRepo diagnosticCenterRepo;

	@Autowired
	BookingRepo bookingRepo;

	@Override
	public ResponseEntity<String> registerUser(String email, String mobile) {
		CustomUser customUser = new CustomUser();
		Optional<CustomUser> userFromDb;
		CustomUser savedUser = null;
		String message = "Primary Registration successful,  for further login please check your : ";
		if (isValidMail(email)) {
			log.info("Registering new user by email");
			userFromDb = userRepository.findByEmail(email);
			if (userFromDb.isPresent()) {
				log.error("This email is already registered, provide another!");
				return ResponseEntity.badRequest().body("This email is already registered, provide another!");
			}
			customUser.setEmail(email);
			customUser.setPassword(OtpGenerationUtil.generatepassword());
			savedUser = userRepository.save(customUser);
			message = message + " email";
		} else if (isValidMobile(mobile)) {
			log.info("invalid email " + email);
			log.info("Registering new user by mobile");
			userFromDb = userRepository.findByMobile(mobile);
			if (userFromDb.isPresent()) {
				log.error("This mobile number is already registered, provide another!");
				return ResponseEntity.badRequest().body("This mobile number is already registered, provide another!");
			}
			customUser.setMobile(mobile);
			customUser.setOtp(OtpGenerationUtil.generateOtp());
			savedUser = userRepository.save(customUser);
			message = message + " otp on mobile";
		} else {
			log.info("invalid mobile too" + mobile);

		}

		if (savedUser != null) {
			return ResponseEntity.ok(message);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("User not registered , Because email and mobile are invalid");
		}
	}

	static Boolean isValidMobile(String mobile) {
		return !(mobile == null || "".equals(mobile));
	}

	static Boolean isValidMail(String email) {
		return !(email == null || "".equals(email));
	}

	@Override
	public ResponseEntity profileSetUp(UpdateProfileRequest updateProfileRequest) {
		Optional<CustomUser> userToBePersisted = Optional.empty();
		CustomUser savedUser = null;
		if (updateProfileRequest != null && updateProfileRequest.getEmail() != null) {
			userToBePersisted = userRepository.findById(updateProfileRequest.getId());
//			userToBePersisted = userRepository.findByEmail(updateProfileRequest.getEmail());
//			userToBePersisted = userRepository.findByUsernameOrEmail(null, updateProfileRequest.getEmail());

			if (userToBePersisted.isEmpty()) {
				throw new RuntimeException("User profile to be updated is not present");
			} else {
				try {
					CustomUser customUser = null;
					customUser = userToBePersisted.get();
					customUser.setUsername(updateProfileRequest.getUsername());
					customUser.setMobile(updateProfileRequest.getMobile());
					customUser.setEmail(updateProfileRequest.getEmail());
					customUser.setName(updateProfileRequest.getName());
					customUser.setPassword(updateProfileRequest.getPassword());
					customUser.setAge(updateProfileRequest.getAge());
					customUser.setGender(updateProfileRequest.getGender());
					customUser.setAddress(updateProfileRequest.getAddress());
					savedUser = userRepository.save(customUser);

				} catch (Exception e) {
					log.error("Exception occured :" + e.getMessage());
				}

			}
		}
		return ResponseEntity.ok(savedUser);
	}

	@Override
	public Boolean loginByOtpOrPassword(User user) {
		Optional<CustomUser> userFromDb;

		if (user != null && user.getEmail() != null && user.getPassword() != null) {
			log.info("Login By email id:");
			userFromDb = userRepository.findByEmail(user.getEmail());

			if (userFromDb.isPresent()) {
				log.info("user found from database");
				if (user.getPassword().equals(userFromDb.get().getPassword())) {
					return true;
				}

			}
		}

		if (user != null && user.getMobile() != null && user.getOtp() != null) {
			log.info("Login By mobile otp:");
			userFromDb = userRepository.findByMobile(user.getMobile());
			if (userFromDb.isPresent()) {
				log.info("user found from database by mobile number");
				if (user.getOtp().equals(userFromDb.get().getOtp())) {
					return true;
				}

			}
		}

		return false;

	}

	@Override
	public ResponseEntity<String> createSlot(CreateSlotRequest createSlotRequest) {

		Optional<DiagnosticCenter> diagnosticCenter = diagnosticCenterRepo
				.findById(createSlotRequest.getDiagnosticCenterId());

		if (diagnosticCenter.isEmpty()) {
			throw new RuntimeException("No such diagnosis center");
		}
		if (createSlotRequest.getDate() != null && createSlotRequest.getDate().isBefore(LocalDate.now())) {
			return ResponseEntity.badRequest().body("Slot cannot be booked on previous date !");
		}
		if (createSlotRequest.getDate().isEqual(LocalDate.now()) && (createSlotRequest.getSlotStartTime() != null
				&& createSlotRequest.getSlotStartTime().isBefore(LocalTime.now()))) {
			return ResponseEntity.badRequest().body("Slot cannot be created before the current time !");
		}
		DiagnosticCenter diagnosticCenterToUdateSlot = diagnosticCenter.get();
		Slot slot = new Slot();
		slot.setDate(createSlotRequest.getDate());
		slot.setSlotStartTime(createSlotRequest.getSlotStartTime());
		slot.setDiagnosticCenter(diagnosticCenterToUdateSlot);
		slot.setIsSlotEnabled(SlotStatus.ENABLED);
		slot.setEndTime(SlotManagementUtil.getSlotEndTime(createSlotRequest.getSlotStartTime()));

		List<Slot> slotList = new ArrayList<>();
		slotList.add(slot);

		diagnosticCenterToUdateSlot.setBookedSlots(slotList);

		diagnosticCenterRepo.save(diagnosticCenterToUdateSlot);

		return ResponseEntity.ok("slot updated to diagnostic center");

	}

	@SuppressWarnings("deprecation")
	@Override
	public void setMaxBooking(int maxBookings, Long slotId) {
		Optional<Slot> slot = slotRepo.findById(slotId);
		if (slot.isEmpty()) {
			throw new RuntimeException("You have enterd Wrong slot, no such a slot ");
		}
		slotRepo.setMaxBooking(maxBookings, slotId);
	}

	@Override
	public void enableOrDisableSlot(SlotStatus slotStatus, Long slotId) {
		slotRepo.enableOrDisableSlot(slotStatus, slotId);

	}

	@Override
	public List<SlotResponseDto> viewAllSlots() {
		List<Slot> slots = slotRepo.findAll();
		List<SlotResponseDto> responseDtos = slots.stream().map(SlotResponseDto::fromEntity)
				.collect(Collectors.toList());
		return responseDtos;

	}

	@Override
	public List<SlotResponseDto> viewBookedSlots() {
		List<Slot> slots = slotRepo.findAll();
		List<SlotResponseDto> responseDtos = slots.stream().filter(s -> s.getBookedAppointments().size() > 0)
				.map(SlotResponseDto::fromEntity).collect(Collectors.toList());
		return responseDtos;

	}
	
	@Override
	public List<SlotResponseDto> viewBookedSlots(LocalDate date) {
		List<Slot> slots = slotRepo.findByDate(date);
		List<SlotResponseDto> responseDtos = slots.stream().filter(s -> s.getBookedAppointments().size() > 0)
				.map(SlotResponseDto::fromEntity).collect(Collectors.toList());
		return responseDtos;
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public BookingAppointmentResponse bookSlot(CreateBookingRequest createBookingRequest) {

		Long slotId = createBookingRequest.getSlotId();
		Optional<CustomUser> patient = userRepository.findById(createBookingRequest.getUserId());
		String message = "";
		System.out.println(new Date());
		Slot slot = slotRepo.findByIdForUpdate(slotId);
		if (patient.isEmpty() || slot == null) {
			throw new RuntimeException("Patient is not registered in Database or Referred slot is not present");
		}
		CustomUser validPatient = patient.get();
		BookingAppointment requestAppointment = null;

		try {

			if (slot != null
					&& (slot.getBookingcount() < slot.getMaxBooking()
							&& !createBookingRequest.getDate().isBefore(LocalDate.now()))
					&& slot.getIsSlotEnabled().equals(SlotStatus.ENABLED)) {

				if(createBookingRequest.getDate().equals(LocalDate.now()) && createBookingRequest.getSlotStartTime().isBefore(LocalTime.now())) {
					return BookingAppointmentResponse.builder().message("Cant book before time").userId(slotId).date(slot.getDate())
							.bookingStatus(BookingStatus.NOT_CONFIRMED).build();
				}
				slot.setBookingcount(slot.getBookingcount() + 1);
				requestAppointment = BookingAppointment.builder().customUser(validPatient).slot(slot)
						.bookingDate(createBookingRequest.getDate())
						.bookingTime(createBookingRequest.getSlotStartTime())
						.BookingStatus(BookingStatus.PENDING_FOR_APPROVAL).build();

				List<BookingAppointment> patientAppointments = validPatient.getBookedAppointments();
				patientAppointments.add(requestAppointment);
				validPatient.setBookedAppointments(patientAppointments);

				CustomUser savedPatient = userRepository.save(validPatient);

				if (savedPatient != null) {
					return BookingAppointmentResponse.builder().userId(slotId).date(slot.getDate())
							.slotStartTime(slot.getSlotStartTime()).bookingStatus(requestAppointment.getBookingStatus())
							.build();

				}
			} else {
				if (slot != null && (slot.getBookingcount() >= slot.getMaxBooking())) {
					message = "Slot is full already!";
					log.error(message);
				}
				if (slot != null && (slot.getIsSlotEnabled().equals(SlotStatus.DISABLED))) {
					message = "Slot is Disabled !";
					log.error(message);
				}
				return BookingAppointmentResponse.builder().message(message).userId(slotId).date(slot.getDate())
						.bookingStatus(BookingStatus.NOT_CONFIRMED).build();
			}
		} catch (PessimisticLockException | CannotAcquireLockException e) {
			log.error("Lock could not be acquired for slotId=" + slotId, e);
			throw new RuntimeException("Slot is currently locked, please retry later.");
		} catch (Exception e) {
			log.error("Exception occured while booking" + e.getMessage());
		}

		return BookingAppointmentResponse.builder().userId(slotId).date(slot.getDate())
				.bookingStatus(BookingStatus.NOT_CONFIRMED).build();

	}

	@Override
	public ResponseEntity cancelBooking(Long id) {
		Optional<BookingAppointment> currentBooking = bookingRepo.findById(id);
		if (currentBooking.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		BookingAppointment booking = currentBooking.get();
		if (!booking.getBookingTime().minusHours(2).isAfter(LocalTime.now())) {
			return ResponseEntity.badRequest().body("Booking cant be cancelled prior to less than two hours!");
		}
		booking.setBookingStatus(BookingStatus.CANCELLED);
		BookingAppointment bookingCancelled = bookingRepo.save(booking);
		if (bookingCancelled == null) {
			return ResponseEntity.badRequest().build();
		} else {
			Slot slotToReduce = bookingCancelled.getSlot();
			slotToReduce.setBookingcount(slotToReduce.getBookingcount() - 1);
			bookingRepo.deleteById(id);
			slotRepo.save(slotToReduce);
			return ResponseEntity.ok("Booking cancelled for id : " + id);
		}
	}



}
