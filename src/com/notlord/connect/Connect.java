package com.notlord.connect;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class Connect {
	/**
	 * Registers Self in 'NotLordConnect'
	 * @return game id of self, returns 0 if already registered, returns -1 if another error occurs
	 * @throws Exception HTTP Request Failed / Reading Request Failed
	 */
	public static int generateGameId() throws Exception {
		URL url = new URL("https://notlord.com/connect/request_host_id:"+getPublicIPv4()+"/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int status = con.getResponseCode();
		if (status == 500) {
			con.disconnect();
			return 0;
		}
		else if (status == 200){
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return Integer.parseInt(content.toString());
		}
		con.disconnect();
		return -1;
	}

	/**
	 * Requests From 'NotLordConnect' Ip Address
	 * @param id Game id
	 * @return Game id, Returns 0 if id is invalid or no server with this id
	 * @throws Exception HTTP Request Failed / Reading Request Failed
	 */
	public static String requestHostIp(int id) throws Exception {
		URL url = new URL("https://notlord.com/connect/request_game_ip:"+id+"/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int status = con.getResponseCode();
		if (status == 200){
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return content.toString();
		}
		con.disconnect();
		return "ERR";
	}

	/**
	 * Removed Selfs's id & Ip From 'NotLordConnect'
	 * @return Was Self Removed?
	 * @throws Exception HTTP Request Failed / Reading Request Failed
	 */
	public static boolean removeGameId() throws Exception {
		URL url = new URL("https://notlord.com/connect/remove_host_id:"+getPublicIPv4()+"/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int status = con.getResponseCode();
		if (status == 200){
			return true;
		}
		con.disconnect();
		return false;
	}

	private static String getPublicIPv4() throws SocketException {
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		String ipToReturn = null;
		while(e.hasMoreElements())
		{
			NetworkInterface n = e.nextElement();
			Enumeration<InetAddress> ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = ee.nextElement();
				String currentAddress = i.getHostAddress();
				if(!i.isSiteLocalAddress()&&!i.isLoopbackAddress() && validate(currentAddress)){
					ipToReturn = currentAddress;
				}else{
					//System.out.println("Address not validated as public IPv4");
				}

			}
		}
		return ipToReturn;
	}

	private static final Pattern IPv4RegexPattern = Pattern.compile(
			"^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	private static boolean validate(final String ip) {
		return IPv4RegexPattern.matcher(ip).matches();
	}
}
