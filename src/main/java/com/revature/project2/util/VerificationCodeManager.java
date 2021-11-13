package com.revature.project2.util;

import java.util.Random;

public class VerificationCodeManager {
	
	public static String generateNewCode() {
		
		Random raw = new Random();
		int code = 10000 + raw.nextInt(89999);
		
		return "-"+code+"-";
	}
	
	public static String[] getCodeFromEmail(String codedEmail) {
		return codedEmail.split("-");
	}
	
}
