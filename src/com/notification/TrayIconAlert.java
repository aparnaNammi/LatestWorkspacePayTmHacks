package com.notification;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

public class TrayIconAlert {
	final static Logger logger = Logger.getLogger(TrayIconAlert.class.getName());

	public void displayTray() throws AWTException, MalformedURLException {
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

		TrayIcon trayIcon = new TrayIcon(image, "Incident alert");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Incident alert");
		tray.add(trayIcon);

		trayIcon.displayMessage("Hello, there is a new incident notification",
				"Incident alert", MessageType.INFO);
	}

}
