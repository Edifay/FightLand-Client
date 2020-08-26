package fight_land.game.render.animation.specific_animation.animationChampion;

import java.util.ArrayList;

import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.Animation;
import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.graphics.Sprites;
import fight_land.game.render.graphics.Texture;

public class AnimationCosmonaute extends AnimationChampionManager {

	public AnimationCosmonaute(GraphicsRender render, Texture texture, ArrayList<Sprites> sprites) {
		super(render, texture, sprites);
	}
	
	public synchronized void attack3(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			resize(14);
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(14).resetSprite();
				this.setAnimationState(14);
				this.animationRunning = new Animation(this.sprites.get(14), this.texture, 15);
				new Thread(()->{
					Texture fuze = new Texture();
					fuze.setLocation(this.texture.getLocation().getX(), this.texture.getLocation().getY()-200);
					fuze.setSize(this.sprites.get(16).getActualSprite().getWidth(), this.sprites.get(15).getActualSprite().getHeight());
					Animation animationFuze = new Animation(this.sprites.get(16), fuze, 10);
					animationFuze.start();
					this.render.addTexture(fuze);
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.render.contentThread(fuze, this.texture.getLocation().getX()+600, this.texture.getLocation().getY(), 150, false);
					animationFuze.stop();
					this.render.remove(fuze);
				}).start(); 
				this.animationRunning.startOne();
				this.canBeCancel = true;
			} else {
				this.sprites.get(15).resetSprite();
				this.setAnimationState(15);
				this.animationRunning = new Animation(this.sprites.get(15), this.texture, 15);
				new Thread(()->{
					Texture fuze = new Texture();
					fuze.setLocation((float) (this.texture.getLocation().getX()-this.sprites.get(15).getActualSprite().getWidth()), this.texture.getLocation().getY()-200);
					fuze.setSize(this.sprites.get(17).getActualSprite().getWidth(), this.sprites.get(15).getActualSprite().getHeight());
					Animation animationFuze = new Animation(this.sprites.get(17), fuze, 10);
					animationFuze.start();
					this.render.addTexture(fuze);
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.render.contentThread(fuze, this.texture.getLocation().getX()-600-this.sprites.get(15).getActualSprite().getWidth(), this.texture.getLocation().getY(), 150, false);
					animationFuze.stop();
					this.render.remove(fuze);
				}).start(); 
				this.animationRunning.startOne();
				this.canBeCancel = true;
			}
		}
	}
}
