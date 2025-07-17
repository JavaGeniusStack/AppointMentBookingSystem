package com.truemed.Abs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.User;
import com.truemed.Abs.Service.UserService;

@RestController
@RequestMapping("/api/login")
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
}
