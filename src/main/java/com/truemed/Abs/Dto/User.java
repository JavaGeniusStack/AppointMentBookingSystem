package com.truemed.Abs.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
	private String username;
	private String email;
	private String mobile;
	private String password;
	private String otp;

}
