package com.gs.service;

import java.security.NoSuchAlgorithmException;

import com.gs.dao.UserDAO;
import com.gs.model.UserType;
import com.gs.utils.SHA1;
import com.sun.mail.util.BASE64DecoderStream;

public class UserService {
	UserDAO dao = new UserDAO();

	public boolean validate(String su, String sp, String pubkey) throws NoSuchAlgorithmException {
		String password = dao.getPassword(new String(BASE64DecoderStream
				.decode(su.getBytes())));
		if (SHA1.sha1(password + pubkey).equals(sp)) {
			return true;
		} else {
			return false;
		}
	}

	public UserType getUserTpye(String su) {
		return null;
	}
	
	public boolean validateSUSP(String su, String SUSP, String pubkey) throws NoSuchAlgorithmException {
		String password = dao.getPassword(new String(BASE64DecoderStream
				.decode(su.getBytes())));
		String sp = SHA1.sha1(password+pubkey);
		if (SHA1.sha1(su + sp + pubkey).equals(SUSP)) {
			return true;
		} else {
			return false;
		}
	}

}
