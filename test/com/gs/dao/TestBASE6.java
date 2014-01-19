package com.gs.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class TestBASE6 {

	@Test
	public void test() {
		String ha = "hahaha";
		String s1 = new String(BASE64EncoderStream.encode(ha.getBytes()));
		String s = new String(BASE64DecoderStream.decode(s1.getBytes()));
		System.out.println(s);
	}

}
