package com.boot.lms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LmsUtility {

	public static BigDecimal parseAmount(String amount)	{
		return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
	}
}
