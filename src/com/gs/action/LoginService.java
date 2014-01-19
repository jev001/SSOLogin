package com.gs.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.service.UserService;
import com.gs.utils.SHA1;

public class LoginService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -709056655632412667L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String su = req.getParameter("su");
		String sp = req.getParameter("sp");
		String url = req.getParameter("url");
		String pubkey = req.getParameter("pubkey");
		String pcid = req.getParameter("pcid");
		String SSO_state = req.getParameter("SSO_state");
		String login_sid = req.getParameter("login_sid");
		UserService service = new UserService();
		boolean validate = false;
		try {
			validate = service.validate(su, sp, pubkey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (!login_sid.equals("") && SSO_state.equals("1") && validate
				&& !pcid.equals("")) {
			Cookie SUSP = null;
			try {
				SUSP = new Cookie("SUSP", SHA1.sha1(su+sp+pubkey));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			SUSP.setMaxAge(86400);
			SUSP.setPath("/");
			Cookie SSO_stateCookie = new Cookie("SSO_state", "2");
			SSO_stateCookie.setPath("/");
			SSO_stateCookie.setMaxAge(86400);
			resp.addCookie(SUSP);
			Cookie sucookie = new Cookie("su", su);
			sucookie.setPath("/");
			sucookie.setMaxAge(86400);
			resp.addCookie(sucookie);
			resp.addCookie(SSO_stateCookie);
			resp.getWriter().write(url);
		}else{
			resp.getWriter().write("ERROR");
		}
		
	}

}
