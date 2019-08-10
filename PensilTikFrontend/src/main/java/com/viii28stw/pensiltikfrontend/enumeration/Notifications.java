package com.viii28stw.pensiltikfrontend.enumeration;

import com.viii28stw.pensiltikfrontend.util.notification.Notification;

public enum Notifications implements Notification {

	INFORMATION("/img/info.png", "#2C54AB"),
	NOTICE("/img/notice.png", "#8D9695"),
	SUCCESS("/img/success.png", "#009961"),
	WARNING("/img/warning.png", "#E23E0A"),
	ERROR("/img/error.png", "#CC0033");

	private final String urlResource;
	private final String paintHex;

	Notifications(String urlResource, String paintHex) {
		this.urlResource = urlResource;
		this.paintHex = paintHex;
	}

	@Override
	public String getURLResource() {
		return urlResource;
	}

	@Override
	public String getPaintHex() {
		return paintHex;
	}

}
