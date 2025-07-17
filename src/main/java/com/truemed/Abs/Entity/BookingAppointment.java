package com.truemed.Abs.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.truemed.Abs.Dto.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingAppointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Version
//	@Builder.Default
//	private Long version = 0L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private CustomUser customUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booked_slot_id", nullable = false)
	private Slot slot;

	private LocalDate bookingDate;

	private LocalTime bookingTime;

	private BookingStatus BookingStatus;
}
