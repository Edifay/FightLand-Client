package fight_land.game.network;

import fight_land.game.network.receiver.Receiver;
import fight_land.game.network.send.Sender;
import networkAPI.Communication;

public class NetWorkManager {
	
	private Receiver receiver;
	private Sender sender;

	public NetWorkManager(Communication com) {
		this.sender = new Sender(com);
		this.receiver = new Receiver(com, this.sender);
	}
	
	public Receiver getReceiver() {
		return this.receiver;
	}
	public Sender getSender() {
		return this.sender;
	}

}
