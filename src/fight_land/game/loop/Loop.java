package fight_land.game.loop;

public class Loop implements Runnable {
	
	@SuppressWarnings("unused")
	private Game game;
	
	public Loop(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}