package com.wenky.commons.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 克林
 * @date 2023/6/8 09:34
 */
@Slf4j
public class IpUtil {
    private static final int PID = getCurrentPid();
    private static final String LOCAL_IP = getLocalIp();

    public static String getIp() {
        return LOCAL_IP;
    }

    public static int getPid() {
        return PID;
    }

    private static int getCurrentPid() {
        int pid = -1;

        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            if (null != jvmName) {
                pid = Integer.parseInt(jvmName.substring(0, jvmName.indexOf(64)));
            }
        } catch (Throwable var2) {
            log.error(var2.getMessage(), var2);
        }

        return pid;
    }

    private static String getLocalIp() {
        try {
            Enumeration netInterfaces;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();
            } catch (SocketException var5) {
                throw new IllegalArgumentException(var5);
            }

            String localIpAddress = null;

            while (netInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) netInterfaces.nextElement();
                Enumeration ipAddresses = netInterface.getInetAddresses();

                while (ipAddresses.hasMoreElements()) {
                    InetAddress ipAddress = (InetAddress) ipAddresses.nextElement();
                    if (isPublicIpAddress(ipAddress)) {
                        return ipAddress.getHostAddress();
                    }

                    if (isLocalIpAddress(ipAddress)) {
                        localIpAddress = ipAddress.getHostAddress();
                    }
                }
            }

            return localIpAddress;
        } catch (Exception var6) {
            log.error(var6.getMessage(), var6);
            return "127.0.0.1";
        }
    }

    private static boolean isPublicIpAddress(InetAddress ipAddress) {
        return !ipAddress.isSiteLocalAddress()
                && !ipAddress.isLoopbackAddress()
                && !isV6IpAddress(ipAddress);
    }

    private static boolean isLocalIpAddress(InetAddress ipAddress) {
        return ipAddress.isSiteLocalAddress()
                && !ipAddress.isLoopbackAddress()
                && !isV6IpAddress(ipAddress);
    }

    private static boolean isV6IpAddress(InetAddress ipAddress) {
        return ipAddress.getHostAddress().contains(":");
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var1) {
            log.error(var1.getMessage(), var1);
            return "--";
        }
    }

    private IpUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp());
    }
}
