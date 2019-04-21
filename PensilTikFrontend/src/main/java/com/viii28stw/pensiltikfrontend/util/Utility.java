package com.viii28stw.pensiltikfrontend.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Utility {
    private static Utility uniqueInstance;

    public static synchronized Utility getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Utility();
        }
        return uniqueInstance;
    }

    public static String getMacAddress() throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        byte[] mac = ni.getHardwareAddress();

        String macAdress = "";
        for (int i = 0; i < mac.length; i++) {
            macAdress += mac[i];
        }
        return macAdress;
    }
}
