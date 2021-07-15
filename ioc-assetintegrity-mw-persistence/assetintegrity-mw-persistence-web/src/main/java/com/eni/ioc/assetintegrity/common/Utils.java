package com.eni.ioc.assetintegrity.common;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class Utils {
	public static final String upper = "ABCDEF";
	public static final String lower = upper.toLowerCase(Locale.ROOT);
	public static final String digits = "0123456789";
	public static final String alphanum = upper + lower + digits;
	private static final Random random = new SecureRandom();
	private static final char[] symbols = (upper + lower + digits).toCharArray();

	public static String nextString() {
		return nextString(16);
	}

	public static String nextString(int lenght) {
		char[] buf = new char[lenght];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}
}
