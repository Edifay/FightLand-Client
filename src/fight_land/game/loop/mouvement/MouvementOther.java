package fight_land.game.loop.mouvement;

import fight_land.game.loop.Game;
import fight_land.game.render.animation.animation_type.AnimationChampionManager;
import fight_land.game.render.collisions.CollisionsDetector;
import fight_land.game.render.graphics.Texture;

public class MouvementOther {

	private Boolean attackPossible = true;
	private int dashNumber = 2;
	private Boolean resetDash = true;

	private final double maxSpeed = 1;
	private final double atAdd = 0.1;

	private final double maxSpeedUp = 5;
	private final double atAddUp = 0.01;
	private final double JumpForce = 3.5;

	private AnimationChampionManager animationManager;
	private Game game;

	private Thread tMouvement;

	private double forceX;
	private double forceY;

	public MouvementOther(Game game, AnimationChampionManager animationManager) {
		this.game = game;
		this.animationManager = animationManager;
	}

	public void start() {
		if (this.tMouvement == null) {
			this.newThreadMouvement();
		}
		if (!this.tMouvement.isAlive()) {
			this.tMouvement.start();
		}
	}

	public void stop() {
		this.tMouvement.interrupt();
	}

	public void newThreadMouvement() {
		this.tMouvement = new Thread(() -> {

			double timeWaited;
			this.forceX = 0;
			this.forceY = 0;

			while (true) {

				if (this.animationManager.getTextureY() > 1080) {
					this.animationManager.forceSetLocation(20, 20);
				}
				
				if(this.animationManager.addBoostY) {
					this.forceY = -1;
					this.animationManager.addBoostY = false;
				}

				timeWaited = System.nanoTime();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeWaited = System.nanoTime() - timeWaited;
				timeWaited = timeWaited / 1000000;

				// ALL Action
				if (this.game.getOtherROULADE() && this.dashNumber > 0 && this.resetDash) {// time dash and double dash
					this.animationManager.roulade(true);
					this.resetDash = false;
					this.dashNumber--;
					new Thread(() -> {
						try {
							Thread.sleep(150);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.resetDash = true;
					}).start();
				}

				if (this.game.getOtherATTACK1() && this.animationManager.canMoveX && this.attackPossible) {
					this.animationManager.attack1WithOutSleep(false);
					this.animationManager.canMoveX = false;
					this.attackPossible = false;
					new Thread(() -> {
						try {
							Thread.sleep(720);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.attackPossible = true;
					}).start();
				}

				if (this.game.getOtherATTACK2()) {
					this.animationManager.attack2(false);
				}

				if (this.game.getOtherSPECIAL()) {
					this.animationManager.attack3(false);
				}

				if (timeWaited <= 4) {// don't skip too much px
					allMovementAndHideBox(timeWaited);
				} else {
					double actual_time_waited = 0;
					while (timeWaited != actual_time_waited) {
						actual_time_waited += 4;
						if (actual_time_waited > timeWaited) {
							allMovementAndHideBox(timeWaited % (actual_time_waited - 4));
							actual_time_waited = timeWaited;
						} else {
							allMovementAndHideBox(4);
						}
					}
				}

			}
		});
	}

	private void allMovementAndHideBox(double timeWaited) {
		Boolean collisionResult;
		if (this.forceY >= 0 && !this.game.getOtherDOWN()) {
			collisionResult = CollisionsDetector.isThisTextureTouchGravity(this.animationManager.getTexture());
		} else {
			collisionResult = false;
		}

		if (this.animationManager.canMoveX) {
			mouvementAxeX(timeWaited);
		}
		if (this.animationManager.canMoveY) {
			mouvementAxeY(timeWaited, collisionResult);
		}

		this.animationManager.forceSetLocation(
				(float) (this.animationManager.getTextureX() + (this.forceX * timeWaited)),
				(float) (this.animationManager.getTextureY() + (this.forceY * timeWaited)));
	}

	private void mouvementAxeY(double timeWaited, Boolean collisionResult) {
		if ((this.forceY < 0 || !collisionResult)) {
			if (this.forceY < maxSpeedUp && !this.game.getOtherDOWN()) {
				this.forceY += atAddUp * timeWaited;
			} else if (CollisionsDetector.isThisTextureTouchGravity(this.animationManager.getTexture())) {
				// travers� toute la plateforme
				Texture text = this.animationManager.getTexture().cloneBound();
				for (int i = 0; i < 20; i++) {
					text.setLocation(text.getLocation().getX(), text.getLocation().getY() + 1);
					if (!CollisionsDetector.isThisTextureTouchGravity(text)) {
						this.animationManager.forceSetLocation(text.getLocation().getX(), text.getLocation().getY());
						break;
					}
				}
				if (CollisionsDetector.isThisTextureTouchGravity(this.animationManager.getTexture())) {
					putToHideBox();
					if (this.dashNumber < 2) {
						this.dashNumber = 2;
					}
				}
			} else {
				this.forceY += atAddUp * timeWaited;
			}
		} else {
			putToHideBox();
			if (this.dashNumber < 2) {
				this.dashNumber = 2;
			}
		}

		if (this.game.getOtherJUMP() && collisionResult) {
			this.forceY = -JumpForce;
		}
	}

	private void putToHideBox() {
		Texture textureCloned = this.animationManager.getTexture().cloneBound();
		int i = 0;
		while (true) {// put at the top of the hidebox for delete hidebox problem
			i++;
			textureCloned.setLocation(textureCloned.getLocation().getX(), textureCloned.getLocation().getY() - 1);
			if (!CollisionsDetector.isThisTextureTouchGravity(textureCloned)) {
				this.animationManager.getTexture().setLocation(this.animationManager.getTexture().getLocation().getX(),
						this.animationManager.getTexture().getLocation().getY() - (i - 1));
				break;
			}
		}
		this.forceY = 0;
		this.animationManager.forceSetLocation((this.animationManager.getTextureX()),
				(int) (this.animationManager.getTextureY()));
	}

	private void mouvementAxeX(double timeWaited) {
		Boolean collisionResult = CollisionsDetector.isThisTextureTouchGravity(this.animationManager.getTexture());
		if (game.getOtherLEFT() && game.getOtherRIGHT()) {
			put0AtForce(timeWaited, collisionResult);
		} else if (game.getOtherRIGHT()) {
			setLeftForce(timeWaited, collisionResult);
		} else if (game.getOtherLEFT()) {
			setRightForce(timeWaited, collisionResult);
		} else {// ramener la force � 0
			put0AtForce(timeWaited, collisionResult);
		}
	}

	private void setLeftForce(double timeWaited, Boolean collisionResult) {
		if (collisionResult) {
			if (this.animationManager.getAnimationState() != 3) {
				this.animationManager.setFlip(false);
				this.animationManager.walk(true);
			}
		} else {
			if (this.animationManager.getAnimationState() != 5) {
				this.animationManager.setFlip(false);
				this.animationManager.fall(true);
			}
		}
		if (this.forceX > -maxSpeed) {
			this.forceX += -(atAdd * timeWaited);
		} else if (this.forceX < -maxSpeed) {
			this.forceX = -maxSpeed;
		}
	}

	private void setRightForce(double timeWaited, Boolean collisionResult) {
		if (collisionResult) {
			if (this.animationManager.getAnimationState() != 2) {
				this.animationManager.setFlip(true);
				this.animationManager.walk(true);
			}
		} else {
			if (this.animationManager.getAnimationState() != 4) {
				this.animationManager.setFlip(true);
				this.animationManager.fall(true);
			}
		}
		if (this.forceX < maxSpeed) {
			this.forceX += (atAdd * timeWaited);
		} else if (this.forceX > maxSpeed) {
			this.forceX = maxSpeed;
		}
	}

	private void put0AtForce(double timeWaited, Boolean collisionResult) {
		if (collisionResult) {
			if (this.animationManager.getAnimationState() != 0) {
				this.animationManager.stand(true);
			}
		} else {
			if (this.animationManager.getAnimationState() != 4 && this.animationManager.getAnimationState() != 5) {
				this.animationManager.fall(true);
			}
		}
		if (this.forceX > atAdd * timeWaited) {
			this.forceX += -(atAdd * timeWaited);
		} else if (this.forceX < -atAdd * timeWaited) {
			this.forceX += (atAdd * timeWaited);
		} else {
			this.animationManager.forceSetLocation((int) (this.animationManager.getTextureX()),
					(int) (this.animationManager.getTextureY()));
			this.forceX = 0;
		}
	}

	public synchronized Boolean getAttackPossible() {
		return this.attackPossible;
	}

	public synchronized void setAttackPossible(Boolean attackPossible) {
		this.attackPossible = attackPossible;
	}

}