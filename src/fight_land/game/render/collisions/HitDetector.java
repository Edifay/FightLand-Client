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
	private Texture championOwner;

	@SuppressWarnings("unchecked")
	public HitDetector(Texture collisionOwner, ArrayList<AnimationChampionManager> playerTexture, ActionHit action) {
		this.collisionOwner = collisionOwner;
		this.playerTexture = (ArrayList<AnimationChampionManager>) playerTexture.clone();
		this.action = action;
		this.tCollision = new Thread();
		this.championOwner = null;
	}

	@SuppressWarnings("unchecked")
	public HitDetector(Texture collisionOwner, ArrayList<AnimationChampionManager> playerTexture, ActionHit action,
			Texture championOwner) {
		this.collisionOwner = collisionOwner;
		this.playerTexture = (ArrayList<AnimationChampionManager>) playerTexture.clone();
		this.action = action;
		this.tCollision = new Thread();
		this.championOwner = championOwner;
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

					Rectangle recOwner = new Rectangle((int) this.collisionOwner.getLocation().getX(),
							(int) this.collisionOwner.getLocation().getY(),
							(int) this.collisionOwner.getSize().getWidth(),
							(int) this.collisionOwner.getSize().getHeight());

					for (int i = 0; i < this.playerTexture.size(); i++) {
						if (this.championOwner == null ? this.playerTexture.get(i).getTexture() != this.collisionOwner
								: this.playerTexture.get(i).getTexture() != this.championOwner) {

							Rectangle recVictim = new Rectangle(
									(int) this.playerTexture.get(i).getTexture().getLocation().getX(),
									(int) this.playerTexture.get(i).getTexture().getLocation().getY(),
									(int) this.playerTexture.get(i).getTexture().getSize().getWidth(),
									(int) this.playerTexture.get(i).getTexture().getSize().getHeight());

							if (conditionRectangle(recOwner, recVictim)) {
								this.action.runActionHit(
										this.championOwner == null ? this.collisionOwner : this.championOwner,
										this.playerTexture.get(i));
								this.playerTexture.remove(i);
							}

						}
					}
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
			}
		});
	}

	public void stop() {
		if (this.tCollision.isAlive())
			this.tCollision.interrupt();
	}

	public Boolean conditionRectangle(Rectangle rec1, Rectangle rec2) {
		return (rec1.getX() < rec2.getX() + rec2.getWidth() 
				&& rec1.getX() + rec1.getWidth() > rec2.getX()
				&& rec1.getY() < rec2.getY() + rec2.getHeight()
				&& rec1.getY() + rec1.getHeight() > rec2.getLocation().getY());
	}
}
