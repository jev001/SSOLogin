package com.gs.model;

import com.google.gson.Gson;

public class PreLoginInfo {

	private String pubkey;
	private String pcid;

	public PreLoginInfo() {
	}
	
	public PreLoginInfo(String pubkey, String pcid) {
		this.pubkey = pubkey;
		this.pcid = pcid;
	}

	public String getPubkey() {
		return pubkey;
	}

	public void setPubkey(String pubkey) {
		this.pubkey = pubkey;
	}

	public String getPcid() {
		return pcid;
	}

	public void setPcid(String pcid) {
		this.pcid = pcid;
	}

	@Override
	public String toString() {
		return "PreLoginInfo ["
				+ (pubkey != null ? "pubkey=" + pubkey + ", " : "")
				+ (pcid != null ? "pcid=" + pcid : "") + "]";
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
