package com.allipper.common.service.comm.utils;

import java.util.Random;

public class CheckingCodeUtil {
	public static String getCheckingCode(int length){
		Random random = new Random();
		char c[] = new char[62];
		for (int i = 97, j = 0; i < 123; i++, j++) {
			c[j] = (char) i;
		}
		for (int o = 65, p = 26; o < 91; o++, p++) {
			c[p] = (char) o;
		}
		for (int m = 48, n = 52; m < 58; m++, n++) {
			c[n] = (char) m;
		}
		String sRand = "";
		for (int i = 0; i < length; i++) {
			int x = random.nextInt(62);
			String rand = String.valueOf(c[x]);
			sRand += rand;
			}
	return sRand;
	}  
}
