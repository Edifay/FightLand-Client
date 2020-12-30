package fight_land.game.loop;

import fight_land.game.Init;

public class Loop implements Runnable {

	@SuppressWarnings("unused")
	private Game game;

	public Loop(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		double x = 0;
		double y = 0;
		int animation = 0;
		while (true) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (x != Init.game.getAnimationManger().getTextureX() || y != Init.game.getAnimationManger().getTextureY()
					|| animation != Init.game.getAnimationManger().getAnimationState()) {
				
				x = Init.game.getAnimationManger().getTextureX();
				y = Init.game.getAnimationManger().getTextureY();
				animation = Init.game.getAnimationManger().getAnimationState();
				
				Init.netManager.getSender().sendLocation(Game.actual_Player_Obj.getID(),
						(int) Init.game.getAnimationManger().getTextureX(),
						(int) Init.game.getAnimationManger().getTextureY(),
						Init.game.getAnimationManger().getAnimationState());
			}
		}
	}
}