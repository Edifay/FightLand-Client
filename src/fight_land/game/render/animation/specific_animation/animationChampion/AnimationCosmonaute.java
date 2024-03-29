package fight_land.game.render.animation.specific_animation.animationChampion;

import fight_land.AssetsContener;
import fight_land.game.loop.Game;
import fight_land.game.render.GraphicsRender;
import fight_land.game.render.animation.Animation;
import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.collisions.ActionHit;
import fight_land.game.render.collisions.HitDetector;
import fight_land.game.render.graphics.Texture;

public class AnimationCosmonaute extends AnimationChampionManager {

	public AnimationCosmonaute(GraphicsRender render, Texture texture, Game game) {
		super(render, texture, AssetsContener.assets.getCosmonauteSprites(), game);
	}

	public synchronized void attack3(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				this.sprites.get(14).resetSprite();
				resize(14);
				this.setAnimationState(14);
				this.animationRunning = new Animation(this.sprites.get(14), this.texture, 15);
				new Thread(() -> {
					Texture fuze = new Texture(true);
					fuze.setLocation(this.texture.getLocation().getX(), this.texture.getLocation().getY() - 200);
					fuze.setSize(this.sprites.get(16).getActualSprite().getWidth(),
							this.sprites.get(15).getActualSprite().getHeight());
					Animation animationFuze = new Animation(this.sprites.get(16), fuze, 10);
					animationFuze.start();
					this.render.addTexture(fuze);
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.render.contentThread(fuze, this.texture.getLocation().getX() + 600,
							this.texture.getLocation().getY(), 150, false);
					animationFuze.stop();
					this.render.remove(fuze);
				}).start();
				this.animationRunning.startOne();
				this.canBeCancel = true;
			} else {
				this.sprites.get(15).resetSprite();
				resize(15);
				this.setAnimationState(15);
				this.animationRunning = new Animation(this.sprites.get(15), this.texture, 15);
				new Thread(() -> {
					Texture fuze = new Texture(true);
					fuze.setLocation(
							(float) (this.texture.getLocation().getX()
									- this.sprites.get(15).getActualSprite().getWidth()),
							this.texture.getLocation().getY() - 200);
					fuze.setSize(this.sprites.get(17).getActualSprite().getWidth(),
							this.sprites.get(15).getActualSprite().getHeight());
					Animation animationFuze = new Animation(this.sprites.get(17), fuze, 10);
					animationFuze.start();
					this.render.addTexture(fuze);
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.render.contentThread(fuze,
							this.texture.getLocation().getX() - 600 - this.sprites.get(15).getActualSprite().getWidth(),
							this.texture.getLocation().getY(), 150, false);
					animationFuze.stop();
					this.render.remove(fuze);
				}).start();
				this.animationRunning.startOne();
				this.canBeCancel = true;
			}
		}
	}

	@Override
	public synchronized void attack2(Boolean canBeCancel) {
		if (this.canBeCancel) {
			this.canBeCancel = canBeCancel;
			super.stopActualAnimation();
			if (this.texture.getRightOrLeft()) {
				Texture moon = new Texture(true);
				moon.setSize(this.sprites.get(12).getActualSprite().getWidth(),
						this.sprites.get(12).getActualSprite().getHeight());
				moon.setLocation(this.texture.getLocation().getX() + 300, this.texture.getLocation().getY() + 50);
				HitDetector collision = new HitDetector(moon, this.game.getChampions(), new ActionHit() {

					@Override
					public void runActionHit(Texture collisionOwner, AnimationChampionManager collisionVictim) {
						new Thread(() -> {
							collisionVictim.tilt(false, collisionOwner, 400, 100);
							collisionVictim.setHP(collisionVictim.getHP() + 0.5f);
						}).start();
					}

				}, this.texture);
				collision.start();
				Animation animationMoon = new Animation(this.sprites.get(12).clone(), moon, 25);
				this.sprites.get(12).resetSprite();
				moon.setImage(this.sprites.get(12).getActualSprite());
				this.render.addTexture(moon);
				this.sprites.get(10).resetSprite();
				resize(10);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.setAnimationState(10);
				this.animationRunning = new Animation(this.sprites.get(10), this.texture, 6);
				new Thread(() -> {
					animationMoon.startOne();
					this.render.remove(moon);
					collision.stop();
				}).start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.animationRunning.startOne();
				this.canBeCancel = true;
			} else {
				this.sprites.get(11).resetSprite();
				resize(11);
				Texture moon = new Texture(true);
				moon.setSize(this.sprites.get(13).getActualSprite().getWidth(),
						this.sprites.get(13).getActualSprite().getHeight());
				moon.setLocation(
						(float) (this.texture.getLocation().getX() - (300 - (this.texture.getSize().getWidth() / 2))),
						this.texture.getLocation().getY() + 50);
				HitDetector collision = new HitDetector(moon, this.game.getChampions(), new ActionHit() {

					@Override
					public void runActionHit(Texture collisionOwner, AnimationChampionManager collisionVictim) {
						new Thread(() -> {
							collisionVictim.tilt(false, collisionOwner, 400, 100);
							collisionVictim.setHP(collisionVictim.getHP() + 0.5f);
						}).start();
					}

				}, this.texture);
				collision.start();
				Animation animationMoon = new Animation(this.sprites.get(13).clone(), moon, 25);
				this.sprites.get(13).resetSprite();
				moon.setImage(this.sprites.get(13).getActualSprite());
				this.render.addTexture(moon);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.setAnimationState(11);
				this.animationRunning = new Animation(this.sprites.get(11), this.texture, 6);
				new Thread(() -> {
					animationMoon.startOne();
					this.render.remove(moon);
					collision.stop();
				}).start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.animationRunning.startOne();
				this.canBeCancel = true;
			}
		}
	}

	public synchronized void startAnimationNumber(int AnimationState) {
		super.stopActualAnimation();
		switch (AnimationState) {
		case 0:
			basicalAnimationOther(AnimationState, 40);
			break;
		case 1:
			basicalAnimationOther(AnimationState, 40);
			break;
		case 2:
			basicalAnimationOther(AnimationState, 25);
			break;
		case 3:
			basicalAnimationOther(AnimationState, 25);
			break;
		case 4:
			basicalAnimationOther(AnimationState, 5);
			break;
		case 5:
			basicalAnimationOther(AnimationState, 5);
			break;
		case 6:
			basicalAnimationOther(AnimationState, 3);
			break;
		case 7:
			basicalAnimationOther(AnimationState, 3);
			break;
		case 8:
			basicalAnimationOther(AnimationState, 9);
			break;
		case 9:
			basicalAnimationOther(AnimationState, 9);
			break;
		case 10:
			basicalAnimationOther(AnimationState, 6);
			new Thread(() -> {
				System.out.println("Starting new Texture for attack animation !");

				Texture text = new Texture(true);
				text.setLocation(this.texture.getLocation().getX() + 300, this.texture.getLocation().getY() + 50);
				text.setSize(AssetsContener.assets.getCosmonauteSprites().get(12).getActualSprite().getWidth(),
						AssetsContener.assets.getCosmonauteSprites().get(12).getActualSprite().getWidth());
				text.setImage(AssetsContener.assets.getCosmonauteSprites().get(12).getActualSprite());

				System.out.println("Affichage de la texture � x : " + text.getLocation().getX() + " y : "
						+ text.getLocation().getY());
				Animation anim = new Animation(AssetsContener.assets.getCosmonauteSprites().get(12).clone(), text, 10);

				fight_land.game.Init.render.addTexture(text);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				anim.start();
				System.out.println("start");
				anim.stop();
				fight_land.game.Init.render.remove(text);
			}).start();
			break;
		case 11:
			basicalAnimationOther(AnimationState, 6);
			break;
		case 14:
			basicalAnimationOther(AnimationState, 15);
			break;
		case 15:
			basicalAnimationOther(AnimationState, 15);
			break;

		default:
			break;
		}
	}
}
