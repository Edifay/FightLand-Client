package fight_land.game.render.animation.animation_type;

import java.util.ArrayList;

import fight_land.game.loop.mouvement.Mouvement;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.Animation;
import fight_land.game.render.animation.AnimationManager;
import fight_land.game.render.graphics.Sprites;
import fight_land.game.render.graphics.Texture;

public abstract class AnimationChampionManager extends AnimationManager {

	public static final Boolean RIGHT = true;
	public static final Boolean LEFT = false;

	private Boolean canBeCancel = true;

	private ArrayList<Sprites> sprites;

	@SuppressWarnings("unchecked")
	public AnimationChampionManager(GraphicsRender render, Texture texture, ArrayList<Sprites> sprites) {
		super(render, texture);
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
			resize(0);
			if (this.texture.getRightOrLeft()) {
				this.animationRunning = new Animation(this.sprites.get(0), this.texture, 25);
				this.animationRunning.start();
			} else {
				this.animationRunning = new Animation(this.sprites.get(1), this.texture, 25);
				this.animationRunning.start();
			}
		}
	}

	public synchronized void walk(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			resize(2);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(2).resetSprite();
				this.setAnimationState(2);
				this.animationRunning = new Animation(this.sprites.get(2), this.texture, 25);
				this.animationRunning.start();
			} else {
				this.sprites.get(3).resetSprite();
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
			resize(4);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(4).resetSprite();
				this.setAnimationState(4);
				this.animationRunning = new Animation(this.sprites.get(4), this.texture, 5);
				this.animationRunning.start();
			} else {
				this.sprites.get(5).resetSprite();
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
			resize(6);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(6).resetSprite();
				this.setAnimationState(6);
				this.animationRunning = new Animation(this.sprites.get(6), this.texture, 3);
				this.animationRunning.start();
				this.render.contentThread(this.texture, this.getTextureX() + 400, this.getTextureY(), 120, false);
			} else {
				this.sprites.get(7).resetSprite();
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
			resize(8);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(8).resetSprite();
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

	public synchronized void attack1WithOutSleep(Mouvement mov, Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			resize(8);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(8).resetSprite();
				this.setAnimationState(8);
				this.animationRunning = new Animation(this.sprites.get(8), this.texture, 9);
				this.animationRunning.start();
				Thread t = new Thread(() -> {
					try {
						Thread.sleep(360);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mov.setMoveX(true);
					this.canBeCancel = true;
				});
				t.start();
			} else {
				this.sprites.get(9).resetSprite();
				this.setAnimationState(9);
				this.animationRunning = new Animation(this.sprites.get(9), this.texture, 9);
				this.animationRunning.start();
				Thread t = new Thread(() -> {
					try {
						Thread.sleep(360);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mov.setMoveX(true);
					this.canBeCancel = true;
				});
				t.start();
			}
		}
	}

	private void resize(int nbTexture) {
		float atall = (float) (this.texture.getLocation().getY() + this.texture.getSize().getHeight());
		float midle = (float) (this.texture.getLocation().getX() + (this.texture.getSize().getWidth() / 2));
		this.forceSetLocation(midle - (this.sprites.get(nbTexture).getActualSprite().getWidth() / 2),
				atall - this.sprites.get(nbTexture).getActualSprite().getHeight());
		this.forceSetSize(this.sprites.get(nbTexture).getActualSprite().getWidth(),
				this.sprites.get(nbTexture).getActualSprite().getHeight());
	}
}