package fight_land.game.network.receiver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fight_land.game.network.send.Sender;
import networkAPI.Communication;
import networkAPI.Packet;

public class Receiver {

	private static Long msStackForRestart = System.currentTimeMillis();
	private long pingUDP;
	private long pingTCP;
	private Communication com;
	private Sender sender;

	public Receiver(Communication com, Sender sender) {
		this.com = com;
		this.sender = sender;
		new Thread(() -> {// start manage TCP

		}).start();
		new Thread(() -> {// start manage UDP
			this.manageUDP();
		}).start();
	}

	public void manageTCP() {
		Long msStack = 0L;

		Packet pack;
		while (this.com != null && this.com.getConnectionTCP().getS().isConnected()) {
			pack = this.com.nextPacket(Communication.TCP);

			switch (pack.getPacketNumber()) {// REQUEST FOR PING
			case 0:
				msStack = this.sender.responsePingTCP(msStack, pack, this);
				break;

			case 1: // get fich at Load
				break;

			default:
				System.out.println("error read Unknow Error");
			}

		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------

	public void manageUDP() {
		Packet pack;

		Long msStack = 0L;

		secureUDPPing();

		while (this.com != null && this.com.getConnectionTCP().getS().isConnected()) {
			pack = this.com.nextPacket(Communication.UDP);

			switch (pack.getPacketNumber()) {

			case 0: {// request for ping
				msStack = this.sender.responsePingUDP(pack, msStack, this);
				break;
			}
			default:
				System.out.println("error read Unknow Error");
			}
		}
	}

	private void secureUDPPing() {
		new Thread(() -> {// continue request ms for secrure UDP
			while (this.com != null) {
				try {
					Thread.sleep(100);
					if (System.currentTimeMillis() - msStackForRestart > 2000) {
						this.com.writeNextPacket(null, Communication.UDP, new Packet(0));
						msStackForRestart = System.currentTimeMillis();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public Object readDataByteToObject(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		try {
			ObjectInputStream inObj = new ObjectInputStream(in);
			return inObj.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] getDataByte(Object obj) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream outobj;
		try {
			outobj = new ObjectOutputStream(out);
			outobj.writeObject(obj);
			outobj.flush();
			outobj.close();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setPingTCP(long ping) {
		this.pingTCP = ping;
	}

	public void setPingUDP(long ping) {
		this.pingUDP = ping;
	}

	public void setMsStackForRestart(long ms) {
		msStackForRestart = ms;
	}

	public long getPingTCP() {
		return this.pingTCP;
	}

	public long getPingUDP() {
		return this.pingUDP;
	}
}