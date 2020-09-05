package fight_land.game.network;

import fight_land.game.network.receiver.Receiver;
import fight_land.game.network.send.Sender;

public class NetWorkManager {
	
	private Receiver receiver;
	private Sender sender;

	public NetWorkManager(Sender sender, Receiver receiver) {
		this.receiver = receiver;
		this.sender = sender;
	}
	
	public Receiver getReceiver() {
		return this.receiver;
	}
	public Sender getSender() {
		return this.sender;
	}

}
