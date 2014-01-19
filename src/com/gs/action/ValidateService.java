package com.gs.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gs.service.UserService;

public class ValidateService extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserService service = new UserService();
		String su = req.getParameter("su");
		String url = req.getParameter("url");
		String pubkey = req.getParameter("pubkey");
		String SUSP = req.getParameter("SUSP");
		String login_sid = req.getParameter("login_sid");
		try {
			if(service.validateSUSP(su, SUSP, pubkey) && !login_sid.equals("")){
				resp.getWriter().write("OK");
				return;
			}else{
				resp.getWriter().write("ERROR");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			resp.getWriter().write("ERROR");
		}
	}

}
