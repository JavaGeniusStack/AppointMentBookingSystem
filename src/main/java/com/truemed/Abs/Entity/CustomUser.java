package com.truemed.Abs.Entity;

import java.util.ArrayList;
import java.util.List;

import com.truemed.Abs.Dto.Gender;
import com.truemed.Abs.Dto.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String mobile;
	private String otp;
	private String name;
	private int age;
	private Gender gender;
	private String address;
	@Builder.Default
	private Roles role = Roles.USER;
	@OneToMany(mappedBy = "customUser", cascade = CascadeType.ALL)
	private List<BookingAppointment> bookedAppointments = new ArrayList<>();
}
