package fight_land.game.render.collisions;

import java.awt.Rectangle;
import java.util.ArrayList;

import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.graphics.Texture;

public class HitDetector {

	private Thread tCollision;
	private Texture collisionOwner;
	private ArrayList<AnimationChampionManager> playerTexture;
	private ActionHit action;

	public HitDetector(Texture collisionOwner, ArrayList<AnimationChampionManager> playerTexture, ActionHit action) {
		this.collisionOwner = collisionOwner;
		this.playerTexture = playerTexture;
		this.action = action;
		this.tCollision = new Thread();
	}

	public void start() {
		if (!this.tCollision.isAlive()) {
			this.stop();
		}
		this.tCollision = newThread();
		this.tCollision.start();
	}

	public Thread newThread() {
		return new Thread(() -> {
			try {
				while (true) {
					
					System.out.println("testing");

					Rectangle recOwner = new Rectangle((int) this.collisionOwner.getLocation().getX(),
							(int) this.collisionOwner.getLocation().getY(),
							(int) this.collisionOwner.getSize().getWidth(),
							(int) this.collisionOwner.getSize().getHeight());

					for (int i = 0; i < this.playerTexture.size(); i++) {
						if (this.playerTexture.get(i).getTexture() != this.collisionOwner) {
							Rectangle recVictim = new Rectangle(
									(int) this.playerTexture.get(i).getTexture().getLocation().getX(),
									(int) this.playerTexture.get(i).getTexture().getLocation().getY(),
									(int) this.playerTexture.get(i).getTexture().getSize().getWidth(),
									(int) this.playerTexture.get(i).getTexture().getSize().getHeight());

							if (conditionRectangle(recOwner, recVictim)) {
								this.action.runActionHit(this.collisionOwner, this.playerTexture.get(i));
							}
						} else {
						}
					}

					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
			}
		});
	}

	public void stop() {
		if (this.tCollision.isAlive()) {
			this.tCollision.interrupt();
		}
	}

	public Boolean conditionRectangle(Rectangle rec1, Rectangle rec2) {
		return (rec1.getX() < rec2.getX() + rec2.getWidth() && rec1.getX() + rec2.getWidth() > rec2.getX()
				&& rec1.getY() < rec1.getY() + rec2.getHeight()
				&& rec1.getY() + rec2.getHeight() > rec2.getLocation().getY());
	}
}
