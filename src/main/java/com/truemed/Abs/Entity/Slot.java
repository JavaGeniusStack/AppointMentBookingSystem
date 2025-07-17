package com.truemed.Abs.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.truemed.Abs.Dto.SlotStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
public class Slot {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Version
//	@Builder.Default
//	private Long version = 0L;
	
	private LocalDate date;

	private LocalTime slotStartTime;

	private LocalTime endTime;

	private int bookingcount;
	
	@Builder.Default
	private int maxBooking = 1;
	
	
	private SlotStatus isSlotEnabled;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diagnostic_center_id", nullable = false)
	private DiagnosticCenter diagnosticCenter;
	
	@OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
	private List<BookingAppointment> bookedAppointments;

	@Override
	public String toString() {
		return "Slot [id=" + id + ", date=" + date + ", slotStartTime=" + slotStartTime + ", endTime=" + endTime + "]";
	}

	
}
