package com.lwt.test;

import java.util.BitSet;

public class T {

	public static void main(String[] args) {
		String value = "11000000";
		int itg = Integer.parseInt(value, 2);
		byte b = (byte)itg;
//		System.out.println(b);
//		System.out.println(Integer.toHexString(b));
	}

}
