package com.notlord.connect;

import java.io.*;
import java.net.*;

public class Connect {
	/**
	 * Registers Self in 'NotLordConnect'
	 * @return game id of self, returns 0 if already registered, returns -1 if another error occurs
	 * @throws Exception HTTP Request Failed / Reading Request Failed
	 */
	public static int generateGameId() throws Exception {
		URL url = new URL("https://notlord.com/connect/request_host_id/");
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
		URL url = new URL("https://notlord.com/connect/remove_host_id/");
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
}
