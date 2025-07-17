package com.truemed.Abs.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truemed.Abs.Entity.BookingAppointment;

public interface BookingRepo extends JpaRepository<BookingAppointment, Long> {

}
