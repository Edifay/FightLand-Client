package fight_land.game.render.animation;

import fight_land.game.render.graphics.Sprites;
import fight_land.game.render.graphics.Texture;

public class Animation {

	private Thread tAnimation;

	private Sprites sprites;

	private Texture textureAtModify;

	private int timeSwap;

	public Animation(Sprites sprites, Texture textureAtModify, int timeSwap) {
		this.sprites = sprites;
		this.textureAtModify = textureAtModify;
		this.timeSwap = timeSwap;
	}

	public void start() {
		if (this.tAnimation != null) {
			if (this.tAnimation.isAlive()) {
				this.stop();
			}
		}
		this.createNewThread();
		this.tAnimation.start();
	}

	public void stop() {
		if (this.tAnimation.isAlive()) {
			this.tAnimation.interrupt();
		}
	}

	public void createNewThread() {
		this.tAnimation = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(this.timeSwap);
					this.textureAtModify.setImage(sprites.getNextSprite());
				}
			} catch (Exception e) {
				System.out.println("stopped animation played");
			}
		});
	}

}
