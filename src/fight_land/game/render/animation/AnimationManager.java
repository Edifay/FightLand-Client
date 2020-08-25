package fight_land.game.render.animation;

import fight_land.game.render.GraphicsRender;
import fight_land.game.render.graphics.Texture;

public abstract class AnimationManager {

	protected Texture texture;
	protected Animation animationRunning;
	protected GraphicsRender render;
	protected int AnimationState;

	public AnimationManager(GraphicsRender render, Texture texture) {
		this.render = render;
		this.texture = texture;
	}

	public void stopActualAnimation() {
		if (this.animationRunning != null) {
			this.animationRunning.stop();
		}
	}

	public void forceSetLocation(float x, float y) {
		this.render.setLocationTexture(this.texture, x, y);
	}

	public void forceSetSize(int width, int height) {
		this.render.setSizeTexture(this.texture, width, height);
	}

	public void forceSetMove(double x, double y, int timeToGo, Boolean stoppable) {
		this.render.moveTexture(this.texture, x, y, timeToGo, stoppable);
	}

	public float getTextureX() {
		return this.texture.getLocation().getX();
	}

	public float getTextureY() {
		return this.texture.getLocation().getY();
	}

	public void setFlip(Boolean rightOrLeft) {
		this.texture.setRightOrLeft(rightOrLeft);
	}

	public Boolean isFlip() {
		return this.texture.getRightOrLeft();
	}

	public void setVisible(Boolean bool) {
		if (bool) {
			if (!this.render.containt(this.texture)) {
				this.render.addTexture(this.texture);
			}
		} else {
			if (this.render.containt(this.texture)) {
				this.render.remove(this.texture);
			}
		}
	}

	public Texture getTexture() {
		return this.texture;
	}

	public int getAnimationState() {
		return this.AnimationState;
	}

	public void setAnimationState(int state) {
		this.AnimationState = state;
	}
}
