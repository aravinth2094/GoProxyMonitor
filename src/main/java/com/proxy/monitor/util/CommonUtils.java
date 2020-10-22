package com.proxy.monitor.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class CommonUtils {

	private CommonUtils() {
		// Avoids Instantiation outside this class
	}

	public static String getIp() throws SocketException {
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		while (e.hasMoreElements()) {
			NetworkInterface n = e.nextElement();
			if (n.isUp() && !n.isLoopback()) {
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements()) {
					InetAddress i = ee.nextElement();
					if (i.isSiteLocalAddress()) {
						return i.getHostAddress();
					}
				}
			}
		}
		return "127.0.0.1";
	}

}
