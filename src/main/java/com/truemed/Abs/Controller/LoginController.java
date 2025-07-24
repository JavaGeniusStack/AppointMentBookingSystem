package com.truemed.Abs.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.AuthRequest;
import com.truemed.Abs.Dto.User;
import com.truemed.Abs.Service.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	@Autowired
	UserService userService;

	@GetMapping("")
	public ResponseEntity loginByEmailWithOtp(@RequestBody User user) {

		if (userService.loginByOtpOrPassword(user)) {
			return ResponseEntity.ok("login ssuccessfull");
		} else
			return ResponseEntity.badRequest().body("Given username Or Otp Or password is invalid");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		try {
			String token = userService.loginByOtpOrPasswordJwt(authRequest);
			if (token != null && !token.isBlank()) {
				return ResponseEntity.ok(Map.of("token", token));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
		}
	}

}
