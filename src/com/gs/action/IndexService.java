package com.gs.action;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6788099401428476440L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cookie c = new Cookie("login_sid",String.valueOf(new Random().nextLong()));
		c.setPath("/");
		c.setMaxAge(86400);
		resp.addCookie(c);
	}

}
