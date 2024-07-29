package com.boot.lms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.boot.lms.exception.UserInputException;

public class LmsUtility {

	public static BigDecimal parseAmount(String amount) {
		try {
			return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			throw new UserInputException("Invalid amount field!");
		}
	}
}
