package com.truemed.Abs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truemed.Abs.Dto.UpdateProfileRequest;
import com.truemed.Abs.Service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity registerUser(@RequestParam String email, @RequestParam String mobile) {

		return userService.registerUser(email, mobile);
	}

	@PutMapping("/updateProfile")
	public ResponseEntity updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
		return userService.profileSetUp(updateProfileRequest);
	}

}
