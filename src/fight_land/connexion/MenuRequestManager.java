package fight_land.connexion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fight_land.lobby.PlayerLobby;
import fight_land.main.Main;
import networkAPI.Communication;
import networkAPI.Packet;

public class MenuRequestManager implements Runnable {

	private Communication com;

	private Long pingUDP;
	private Long pingTCP;

	private static Long msStackForRestart = System.currentTimeMillis();

	public MenuRequestManager(Communication com) {
		this.com = com;
	}

	@Override
	public void run() {
		new Thread(() -> {
			this.manageTCP();
		}).start();
		this.manageUDP();
	}

	public void manageTCP() {
		Long msStack = 0L;

		// first packet ping
		this.com.writeNextPacket(null, Communication.TCP, new Packet(0));

		// Send first info
		sendName();

		Packet pack;
		while (this.com != null && this.com.getConnectionTCP().getS().isConnected()) {
			pack = this.com.nextPacket(Communication.TCP);

			switch (pack.getPacketNumber()) {// REQUEST FOR PING
			case 0: {
				msStack = responsePingTCP(msStack, pack);
				break;
			}
			case 1: {// start lobby
				fight_land.lobby.Init.lob.start();
				break;
			}
			case 2: {// get new player at add
				fight_land.lobby.Init.lob.getPlayers()
						.add(new PlayerLobby((String) readDataByteToObject(pack.getData().get(0)),
								(int) readDataByteToObject(pack.getData().get(1))));
				break;
			}
			case 3: {// get player at remove
				String name = (String) readDataByteToObject(pack.getData().get(0));
				for (int i = 0; i < fight_land.lobby.Init.lob.getPlayers().size(); i++) {
					if (fight_land.lobby.Init.lob.getPlayers().get(i).getName().equals(name)) {
						fight_land.lobby.Init.lob.getPlayers().remove(i);
					}
				}
				break;
			}
			case 4: {// set graphics player
				String name = (String) readDataByteToObject(pack.getData().get(0));
				for (int i = 0; i < fight_land.lobby.Init.lob.getPlayers().size(); i++) {
					if (fight_land.lobby.Init.lob.getPlayers().get(i).getName().equals(name)) {
						fight_land.lobby.Init.lob.getPlayers().get(i)
								.setGraphicsNumber((int) readDataByteToObject(pack.getData().get(1)));
					}
				}
				break;
			}
			case 5: {// restart
				fight_land.lobby.Init.lob.restartLobby();
				fight_land.lobby.Init.lob.setTimer_lobby(5);
				break;
			}
			case 6: {// start game
				System.out.println("startGame");
				this.com = null;
				fight_land.game.Init.init(this.com);
				// TODO start game
				break;
			}
			default:
				System.out.println("error read Unknow Error");
			}

		}
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void manageUDP() {
		Packet pack;

		this.com.writeNextPacket(null, Communication.UDP, new Packet(0));

		Long msStack = 0L;

		secureUDPPing();

		while (this.com != null && this.com.getConnectionTCP().getS().isConnected()) {
			pack = this.com.nextPacket(Communication.UDP);

			switch (pack.getPacketNumber()) {

			case 0: {// request for ping
				msStack = responsePing(pack, msStack);
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

	private Long responsePing(Packet pack, Long msStack) {
		if (msStack != 0) {
			msStackForRestart = System.currentTimeMillis();
			pingUDP = System.currentTimeMillis() - msStack;
			msStack = 0L;
			new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					this.com.writeNextPacket(null, Communication.UDP, new Packet(0));
				} catch (Exception e) {
				}
			}).start();
		} else {
			msStack = System.currentTimeMillis();
			this.com.writeNextPacket(null, Communication.UDP, pack);
		}
		return msStack;
	}

	public synchronized Long getPingUDP() {
		return this.pingUDP;
	}

	public synchronized void setPingUDP(Long pingUDP) {
		this.pingUDP = pingUDP;
	}

	public synchronized Long getPingTCP() {
		return this.pingTCP;
	}

	public synchronized void setPingTCP(Long pingTCP) {
		this.pingTCP = pingTCP;
	}

	public void sendJoinLobby() {
		this.com.writeNextPacket(null, Communication.TCP, new Packet(3));
	}

	public void sendReadtToPlay() {
		this.com.writeNextPacket(null, Communication.TCP, new Packet(2));
	}

	private Long responsePingTCP(Long msStack, Packet pack) {
		if (msStack != 0) {
			this.pingTCP = System.currentTimeMillis() - msStack;
			msStack = 0L;
			new Thread(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					this.com.writeNextPacket(null, Communication.TCP, new Packet(0));
				} catch (Exception e) {
				}
			}).start();
		} else {
			msStack = System.currentTimeMillis();
			this.com.writeNextPacket(null, Communication.TCP, pack);
		}
		return msStack;
	}

	private void sendName() {
		Packet packetName = new Packet(2);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(baos);
			out.writeUTF(Main.name);
			out.flush();
			packetName.add(baos.toByteArray());

			this.com.writeNextPacket(null, Communication.TCP, packetName);

			out.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void sendSelect(int select_nb) {
		Packet pack = new Packet(3);
		pack.add(getDataByte(select_nb));
		this.com.writeNextPacket(null, Communication.TCP, pack);
	}
}
