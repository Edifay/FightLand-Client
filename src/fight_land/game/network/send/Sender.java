package fight_land.game.network.send;

import fight_land.game.network.receiver.Receiver;
import networkAPI.Communication;
import networkAPI.Packet;

public class Sender {
	
	private Communication com;
	
	public Sender(Communication com) {
		this.com = com;
	}
	
	public Long responsePingTCP(Long msStack, Packet pack, Receiver receiver) {
		if (msStack != 0) {
			receiver.setPingTCP(System.currentTimeMillis() - msStack); 
			System.out.println("ping TCP : "+(System.currentTimeMillis() - msStack));
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
	
	public Long responsePingUDP(Packet pack, Long msStack, Receiver receiver) {
		if (msStack != 0) {
			receiver.setMsStackForRestart(System.currentTimeMillis());
			receiver.setPingUDP(System.currentTimeMillis() - msStack);
			msStack = 0L;
			System.out.println("UDP ping : "+(System.currentTimeMillis() - msStack));
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

}
