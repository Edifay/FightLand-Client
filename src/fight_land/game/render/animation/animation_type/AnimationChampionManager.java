package fight_land.game.render.animation.animation_type;

import java.util.ArrayList;

import fight_land.game.Point;
import fight_land.game.loop.Game;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.Animation;
import fight_land.game.render.animation.AnimationManager;
import fight_land.game.render.graphics.Sprites;
import fight_land.game.render.graphics.Texture;

public abstract class AnimationChampionManager extends AnimationManager {

	public static final Boolean RIGHT = true;
	public static final Boolean LEFT = false;

	protected Boolean canBeCancel = true;

	protected ArrayList<Sprites> sprites;

	public Boolean canMoveX;
	public Boolean canMoveY;

	public Boolean addBoostY;

	@SuppressWarnings("unchecked")
	public AnimationChampionManager(GraphicsRender render, Texture texture, ArrayList<Sprites> sprites, Game game) {
		super(render, texture, game);
		this.canMoveX = true;
		this.canMoveY = true;
		this.addBoostY = false;
		this.sprites = (ArrayList<Sprites>) sprites.clone();
		for (int i = 0; i < this.sprites.size(); i++) {
			this.sprites.set(i, this.sprites.get(i).clone());
		}
	}

	public synchronized void stand(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			this.setAnimationState(0);
			if (this.texture.getRightOrLeft()) {
				resize(0);
				this.animationRunning = new Animation(this.sprites.get(0), this.texture, 40);
				this.animationRunning.start();
			} else {
				resize(1);
				this.animationRunning = new Animation(this.sprites.get(1), this.texture, 40);
				this.animationRunning.start();
			}
		}
	}

	public synchronized void walk(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(2).resetSprite();
				resize(2);
				this.setAnimationState(2);
				this.animationRunning = new Animation(this.sprites.get(2), this.texture, 25);
				this.animationRunning.start();
			} else {
				this.sprites.get(3).resetSprite();
				resize(3);
				this.setAnimationState(3);
				this.animationRunning = new Animation(this.sprites.get(3), this.texture, 25);
				this.animationRunning.start();
			}
		}
	}

	public synchronized void fall(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(4).resetSprite();
				resize(4);
				this.setAnimationState(4);
				this.animationRunning = new Animation(this.sprites.get(4), this.texture, 5);
				this.animationRunning.start();
			} else {
				this.sprites.get(5).resetSprite();
				resize(5);
				this.setAnimationState(5);
				this.animationRunning = new Animation(this.sprites.get(5), this.texture, 5);
				this.animationRunning.start();
			}
		}
	}

	public synchronized void roulade(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(6).resetSprite();
				resize(6);
				this.setAnimationState(6);
				this.animationRunning = new Animation(this.sprites.get(6), this.texture, 3);
				this.animationRunning.start();
				this.render.contentThread(this.texture, this.getTextureX() + 400, this.getTextureY(), 120, false);
			} else {
				this.sprites.get(7).resetSprite();
				resize(7);
				this.setAnimationState(7);
				this.animationRunning = new Animation(this.sprites.get(7), this.texture, 3);
				this.animationRunning.start();
				this.render.contentThread(this.texture, this.getTextureX() - 400, this.getTextureY(), 120, false);
			}
		}
	}

	public synchronized void attack1(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(8).resetSprite();
				resize(8);
				this.setAnimationState(8);
				this.animationRunning = new Animation(this.sprites.get(8), this.texture, 9);
				this.animationRunning.start();
				try {
					Thread.sleep(360);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.canBeCancel = true;
			} else {
				this.sprites.get(9).resetSprite();
				resize(9);
				this.setAnimationState(9);
				this.animationRunning = new Animation(this.sprites.get(9), this.texture, 9);
				this.animationRunning.start();
				try {
					Thread.sleep(360);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.canBeCancel = true;
			}
		}
	}

	public synchronized void attack1WithOutSleep(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(8).resetSprite();
				resize(8);
				this.setAnimationState(8);
				this.animationRunning = new Animation(this.sprites.get(8), this.texture, 9);
				Thread t = new Thread(() -> {
					this.animationRunning.startOne();
					this.canMoveX = true;
					this.canBeCancel = true;
				});
				t.start();
			} else {
				this.sprites.get(9).resetSprite();
				resize(9);
				this.setAnimationState(9);
				this.animationRunning = new Animation(this.sprites.get(9), this.texture, 9);
				Thread t = new Thread(() -> {
					this.animationRunning.startOne();
					this.canMoveX = true;
					this.canBeCancel = true;
				});
				t.start();
			}
		}
	}

	public abstract void attack2(Boolean canBeCancel);

	public abstract void attack3(Boolean canBeCancel);

	public void tilt(Boolean canBeCancel, Texture championOwner, int XExplosion, int YExplosion) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getLocation().getX() > championOwner.getLocation().getX()) {
				this.texture.setRightOrLeft(true);
			} else {
				this.texture.setRightOrLeft(false);
			}
			if (this.texture.getRightOrLeft()) {
				this.canMoveY = false;
				this.canMoveX = false;
				this.sprites.get(18).resetSprite();
				resize(18);
				this.setAnimationState(18);
				this.animationRunning = new Animation(this.sprites.get(18), this.texture, 5);
				this.animationRunning.start();
				Point end = new Point((XExplosion*this.game.getHP()) + this.texture.getLocation().getX(),
						-(YExplosion*this.game.getHP()) + this.texture.getLocation().getY());

				double distanceEndPointToActual = Math.sqrt(((end.getX() - this.texture.getLocation().getX())
						* (end.getX() - this.texture.getLocation().getX()))
						+ ((end.getY() - this.texture.getLocation().getY())));

				System.out.println("distance : " + distanceEndPointToActual);
				this.render.contentThread(this.texture, end.getX(), end.getY(),
						(int) (300 * ((distanceEndPointToActual/this.game.getHP()) / 600)), false);
				this.addBoostY = true;
				this.canMoveY = true;
				this.canMoveX = true;
				this.canBeCancel = true;
			} else {
				this.canMoveY = false;
				this.canMoveX = false;
				this.sprites.get(19).resetSprite();
				resize(19);
				this.setAnimationState(19);
				this.animationRunning = new Animation(this.sprites.get(19), this.texture, 5);
				this.animationRunning.start();
				Point end = new Point((-(XExplosion*this.game.getHP())) + this.texture.getLocation().getX(),
						-(YExplosion*this.game.getHP()) + this.texture.getLocation().getY());

				double distanceEndPointToActual = Math.sqrt(((end.getX() - this.texture.getLocation().getX())
						* (end.getX() - this.texture.getLocation().getX()))
						+ ((end.getY() - this.texture.getLocation().getY())));

				System.out.println("distance : " + distanceEndPointToActual);
				this.render.contentThread(this.texture, end.getX(), end.getY(),
						(int) (300 * ((distanceEndPointToActual/(this.game.getHP())) / 600)), false);

				System.out.println("x : " + end.getX() + " y : " + end.getY());
				this.addBoostY = true;
				this.canMoveY = true;
				this.canMoveX = true;
				this.canBeCancel = true;
			}
		}
	}

	protected void resize(int nbTexture) {
		float atall = (float) (this.texture.getLocation().getY() + this.texture.getSize().getHeight());
		float midle = (float) ((float) (this.texture.getLocation().getX() + (this.texture.getSize().getWidth() / 2))
				- 0.5);
		this.forceSetLocation(midle - (this.sprites.get(nbTexture).getActualSprite().getWidth() / 2),
				atall - this.sprites.get(nbTexture).getActualSprite().getHeight());
		this.forceSetSize(this.sprites.get(nbTexture).getActualSprite().getWidth(),
				this.sprites.get(nbTexture).getActualSprite().getHeight());
		this.texture.setImage(this.sprites.get(nbTexture).getActualSprite());
	}
	
	public void setHP(float hp) {
		this.game.setHP(hp);
	}
	
	public float getHP() {
		return this.game.getHP();
	}
}