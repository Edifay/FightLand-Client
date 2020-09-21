package fight_land.game.network.receiver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fight_land.game.Init;
import fight_land.game.loop.Player;
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
			this.manageTCP();
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

				@SuppressWarnings("unchecked")
				ArrayList<Integer> nbAtLoad = (ArrayList<Integer>) readDataByteToObject(pack.getData().get(0));
				new Thread(() -> {
					ArrayList<Integer> nbAfter = new ArrayList<Integer>();
					for (int i = 0; i < nbAtLoad.size(); i++) {
						Boolean find = false;
						for (int a = 0; a < nbAfter.size(); a++) {
							if (nbAfter.get(a).intValue() == nbAtLoad.get(i).intValue()) {
								find = true;
							}
						}
						if (!find)
							nbAfter.add(nbAtLoad.get(i));

					}
					for (int i = 0; i < nbAfter.size(); i++)
						Init.loader.load(nbAfter.get(i).intValue());

					Init.loader.setLoadFinish(true);
				}).start();
				break;

			case 2:
				ArrayList<byte[]> bytes = pack.getData();
				for (int i = 0; i < bytes.size(); i++) {
					@SuppressWarnings("unchecked")
					ArrayList<Object> playerInfo = (ArrayList<Object>) readDataByteToObject(bytes.get(i));

					Init.game.getPlayers().add(new Player((Long) playerInfo.get(0), (Boolean) playerInfo.get(1),
							(Integer) playerInfo.get(2), (String) playerInfo.get(3)));

					System.out.println("Creation du perso ID :" + (long) playerInfo.get(0) + " YOU ? : "
							+ (Boolean) playerInfo.get(1) + " Texture Select : " + (int) playerInfo.get(2) + " Name : "
							+ (String) playerInfo.get(3));

				}
				Init.game.setPlayer_loaded(true);
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

			case 0: // request for ping
				msStack = this.sender.responsePingUDP(pack, msStack, this);
				break;

			case 1: // get Location player
				long ID = (long) readDataByteToObject(pack.getData().get(0));

				for (int i = 0; i < Init.game.getPlayers().size(); i++) {
					if (Init.game.getPlayers().get(i).getID() == ID && !Init.game.getPlayers().get(i).getIsMe()) {
//						System.out.println("Get Location Player :" + Init.game.getPlayers().get(i).getName());
						Init.game.getPlayers().get(i).getAnimationManger().forceSetMove(
								(int) readDataByteToObject(pack.getData().get(1)),
								(int) readDataByteToObject(pack.getData().get(2)), 25, true);
						int animationState = (int) readDataByteToObject(pack.getData().get(3));
						if (Init.game.getPlayers().get(i).getAnimationManger().getAnimationState() != animationState) {
							Init.game.getPlayers().get(i).getAnimationManger().startAnimationNumber(animationState);
						}
						break;
					}
				}
				break;
			default:
				System.out.println("error read Unknow Error :"+pack.getPacketNumber());
				break;
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
		})/*.start();*/;
	}

	public static Object readDataByteToObject(byte[] data) {
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