package com.truemed.Abs.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileRequest {
	private Long id;
	private String username;
	private String email;
	private String password;
	private String mobile;
	private String name;
	private int age;
	private Gender gender;
	private String address;

}
