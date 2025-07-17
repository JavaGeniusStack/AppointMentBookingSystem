package com.truemed.Abs.Entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class DiagnosticCenter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String centerName;
	private String location;
	private String contactInfo;
	private int rating;
	private List<String> reviews;
	@OneToMany(mappedBy = "diagnosticCenter", cascade = CascadeType.ALL)
	private List<Slot> bookedSlots = new ArrayList<>();

	@OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
	private List<DiagnosticTest> tests;

}
