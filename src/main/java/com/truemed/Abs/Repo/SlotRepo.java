package com.truemed.Abs.Repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.truemed.Abs.Dto.SlotStatus;
import com.truemed.Abs.Entity.Slot;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

public interface SlotRepo extends JpaRepository<Slot, Long> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE Slot SET maxBooking = ?1 WHERE id=?2")
	void setMaxBooking(int maxBookings, Long slotId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Slot SET isSlotEnabled = ?1 WHERE id = ?2")
	void enableOrDisableSlot(SlotStatus isSlotEnabled, Long slotId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Slot s WHERE s.id = ?1")
	Slot findByIdForUpdate(Long id);
	
	List<Slot> findByDate(LocalDate date);
	

}
