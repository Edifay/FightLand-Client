package fight_land.connexion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import networkAPI.Communication;

public class Connect {

	public static Communication com;

	public static final String ipName = "localhost";
	public static final int port = 7630;

	public static InetAddress ip;

	public static MenuRequestManager manager = null;

	public static void connect() {
		try {
			ip = InetAddress.getByName(ipName);
			System.out.println("setup COnnect");
			com = new Communication();
			com.setCommunicationPostion(Communication.CLIENT);
			com.setConnectionType(Communication.TCP_AND_UDP);
			com.setIp(ip);
			com.setPort(port);
			com.setConnect(true);
			com.create();
			System.out.println("Connected");
			manager = new MenuRequestManager(com);
			new Thread(manager).start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
