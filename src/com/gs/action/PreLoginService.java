package com.gs.action;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.model.PreLoginInfo;


public class PreLoginService extends HttpServlet {

	private static final long serialVersionUID = -2651430616398372911L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		String login_sid = "";
		for(Cookie c : cookies){
			if(c.getName().equals("login_sid")){
				login_sid = c.getValue();
			}
		}
		PreLoginInfo info = new PreLoginInfo(UUID.randomUUID().toString(),String.valueOf(new Random().nextLong()));
		if (!login_sid.equals("")) {
			Cookie c = new Cookie("SSO_state","1");
			c.setPath("/");
			c.setMaxAge(86400);
			resp.addCookie(c);
			resp.getWriter().write(info.toJson());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("index.jsp");
	}

}
