package com.truemed.Abs.Utility;

import java.util.Random;

public class OtpGenerationUtil {
	public static String generateOtp() {
		Random random = new Random();
		int otp = random.nextInt(1234, 9999);
		return String.valueOf(otp);
	}

	public static String generatepassword() {
		return "password";
	}
}
